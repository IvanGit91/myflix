package me.personal.myflix.service.base.impl;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import me.personal.myflix.annotation.Recursive;
import me.personal.myflix.entity.base.BaseEntity;
import me.personal.myflix.entity.base.BaseOriginalEntity;
import me.personal.myflix.enums.ASSOCIATION_ENUM;
import me.personal.myflix.repository.base.BaseRepository;
import me.personal.myflix.service.base.BaseService;
import me.personal.myflix.config.framework.application_context.ApplicationContextProvider;
import me.personal.myflix.utility.utils.UtilsReflection;
import me.personal.myflix.dto.FieldList;
import org.hibernate.annotations.NaturalId;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;



public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {
	@Getter
	private final BaseRepository<T,ID> baseRepository;

    @PersistenceContext
	private EntityManager entityManager;

    protected BaseServiceImpl(BaseRepository<T, ID> baseRepository, Class<T> type) {
        this.baseRepository = baseRepository;
        this.type = type;
    }

	private final Class<T> type;

	@Override
	public T save(T entity){
		return saveUpdateListsMultiRecursive(entity, null) == null ? getBaseRepository().save(entity) : entity;
	}

	// Manuale
	@Override
	@Deprecated(since="0.0.1", forRemoval=true)
    /**
     * @deprecated
     */
	public boolean saveLists(T entity){
        // List saving logic to be implemented in dedicated classes, if there are no lists the method returns true and performs the save
		return true;
	}

	// if it has only one field at one level of depth
	@Override
	@Deprecated(since="0.0.1", forRemoval=true)
	public boolean saveListsSingle(T entity, ID id){
		boolean resave = false;
		List<Field> fields = UtilsReflection.getAnnotatedFields(type, OneToMany.class);
		for (Field field: fields) {
			String fieldName = field.getName();
			OneToMany oneToManyAnnotation = field.getAnnotation(OneToMany.class);
			String mappedBy = oneToManyAnnotation.mappedBy();
			List<?> lista = (List<?>) UtilsReflection.callGetter(entity, fieldName);
			if (id == null) {
				UtilsReflection.callSetter(entity, fieldName, null);
				getBaseRepository().save(entity);
			}
			if (lista != null && !lista.isEmpty()) {
				lista.forEach(e -> UtilsReflection.callSetter(e, mappedBy, entity));
				UtilsReflection.callSetter(entity, fieldName, lista);
				resave = true;
			}
		}
		return resave;
	}

	// If it has multiple fields at one level of depth
	@Override
	@Deprecated(since="0.0.1", forRemoval=true)
	public boolean saveListsMulti(T entity, ID id){
		boolean resave = false;
		List<FieldList<?>> liste = new ArrayList<>();
		List<Field> fields = UtilsReflection.getAnnotatedFields(type, OneToMany.class);
		for (Field field: fields) {
			String fieldName = field.getName();
			OneToMany oneToManyAnnotation = field.getAnnotation(OneToMany.class);
			String mappedBy = oneToManyAnnotation.mappedBy();
			List<T> lista = (List<T>) UtilsReflection.callGetter(entity, fieldName);
			if (id == null) {
				liste.add(new FieldList<>(fieldName, mappedBy, lista, true, ASSOCIATION_ENUM.ONE_TO_MANY, null, null));
				UtilsReflection.callSetter(entity, fieldName, null);
			}
		}
		if (id == null) {
			getBaseRepository().save(entity);
		}
		if (!liste.isEmpty()) {
			liste.forEach(e -> {
				e.getList().forEach(l -> UtilsReflection.callSetter(l, e.getMappedBy(), entity));
				UtilsReflection.callSetter(entity, e.getFieldName(), e.getList());
			});
			resave = true;
		}
		return resave;
	}

	// If it has multiple fields at multiple levels of depth
	// TODO - recursive without reference id are not persisted
	@Override
	public ID saveUpdateListsMultiRecursive(T entity, String prevMappedBy){
		ID id = entity.getId(), idBack = null;
		String fieldName, mappedBy = "", manyToManyEmbeddedIdField, manyToManyMapFieldName = "";
		Class<?> manyToManyMapFieldClass;
		ASSOCIATION_ENUM association;
		EmbeddedId embeddedId = null;
		T embeddedIdEntity = null, nextEntity;
		List<T> entityList = new ArrayList<>();
		List<FieldList<T>> liste = new ArrayList<>();
		List<Field> fields;

		fields = UtilsReflection.getAnnotatedFields(entity.getClass(), EmbeddedId.class);
		if (!fields.isEmpty()) {
			Field f = fields.get(0); // Should exist only one case in which there are many-to-many with additional fields
			embeddedId = f.getAnnotation(EmbeddedId.class);
			if (embeddedId != null) {
				manyToManyEmbeddedIdField = f.getName();
				manyToManyMapFieldClass = f.getType();
				embeddedIdEntity = (T) UtilsReflection.callGetter(entity, manyToManyEmbeddedIdField);
				if (embeddedIdEntity == null) {  // Case without passing embeddedID field (embeddedId)
					embeddedIdEntity = UtilsReflection.callDefaultConstructor(manyToManyMapFieldClass);
					UtilsReflection.callSetter(entity, manyToManyEmbeddedIdField, embeddedIdEntity); // Call the constructor of embeddedID field if null
				}
			}
		}
		fields = UtilsReflection.getAnnotatedFields(entity.getClass(), List.of(OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class));
		if (fields.isEmpty()) { // If it hasn't field to check, it returns
			return null;
		}
		else {
			for (Field field : fields) {
				fieldName = field.getName();
                ID fieldID = null;
				//Recursive recursiveAnnotation = field.getAnnotation(Recursive.class);  // Check FK recursive fields
				//Referenced referencedEntity = field.getAnnotation(Referenced.class);
				OneToOne oneToOneAnnotation = field.getAnnotation(OneToOne.class);
				ManyToMany manyToManyAnnotation = field.getAnnotation(ManyToMany.class);
				OneToMany oneToManyAnnotation = field.getAnnotation(OneToMany.class);
				ManyToOne manyToOneAnnotation = field.getAnnotation(ManyToOne.class); // for the one-to-many association
				MapsId mapsId = field.getAnnotation(MapsId.class); // for the manyToMany with additional fields
				if (oneToOneAnnotation != null || manyToOneAnnotation != null) {
                    if (entityList == null) {
                        entityList = new ArrayList<>();
                    }
					if (!entityList.isEmpty()) {
                        entityList.clear();
                    }
					T oneEntity = (T) UtilsReflection.callGetter(entity, fieldName);
                    fieldID = oneEntity.getId();
					if (oneToOneAnnotation != null) {
						association = ASSOCIATION_ENUM.ONE_TO_ONE;
						mappedBy = oneToOneAnnotation.mappedBy();
					} else { // MANY_TO_ONE
						if (mapsId != null) {
							association = ASSOCIATION_ENUM.MANY_TO_ONE_TO_MANY;
							manyToManyMapFieldName = mapsId.value();
						}
						else
							association = ASSOCIATION_ENUM.MANY_TO_ONE;
					}
                    entityList.add(oneEntity);
				} else {
					if (oneToManyAnnotation != null) {
						association = ASSOCIATION_ENUM.ONE_TO_MANY;
						mappedBy = oneToManyAnnotation.mappedBy();
					} else { // MANYTOMANY
						association = ASSOCIATION_ENUM.MANY_TO_MANY;
						mappedBy = manyToManyAnnotation.mappedBy();
					}
					entityList = (List<T>) UtilsReflection.callGetter(entity, fieldName);
				}
                // For the annotation that do not have the mappedBy, I find the mapped field by exploiting that they have the same class
				if (entityList != null && !entityList.isEmpty()) {
					for (int i=0; i<entityList.size(); i++) {
						nextEntity = entityList.get(i);
						if (!(nextEntity instanceof HibernateProxy)) {
                            List<Field> fieldsMappedBy = UtilsReflection.equalsTargetClassFieldMappedBy(nextEntity.getClass(), fieldName);
                            if (mappedBy.isEmpty() && !fieldsMappedBy.isEmpty()) {
                                mappedBy = fieldsMappedBy.get(0).getName();
                            }
							if (!mappedBy.isEmpty() && !nextEntity.getClass().equals(entity.getClass()) && (prevMappedBy == null || !prevMappedBy.equals(fieldName))) { // If it has recursion, don't check elements otherwise it goes in loop
                                idBack = saveUpdateListsMultiRecursive(nextEntity, mappedBy); //Do recursion and check field by field
							}
						} else {
							entityList.remove(i--);
						}
					}

					if (!entityList.isEmpty()) {
                        // In this way also update works
						liste.add(new FieldList<>(fieldName, mappedBy, new ArrayList<>(entityList),id == null, association, idBack, manyToManyMapFieldName));
						// If the id is still null, save it and make the list null
						if (id == null && fieldID == null) {
							UtilsReflection.callSetter(entity, fieldName, null);
						}
					}
				}
			}

            // Save the entity added
			if (id == null) {
				if (embeddedId != null) {  // Many-to-many association with additional fields
					T finalEmbeddedIdEntity = embeddedIdEntity;
					liste.stream().filter(e -> e.getAssociation().equals(ASSOCIATION_ENUM.MANY_TO_ONE_TO_MANY))
							.forEach(e -> {
								UtilsReflection.callSetter(finalEmbeddedIdEntity, e.getMapFieldName(), e.getId());
								//e.getList().forEach(l -> UtilsReflection.callSetter(l, e.getMappedBy(), entity));
								UtilsReflection.callSetter(entity, e.getFieldName(), e.getList().get(0));
							});
					if (prevMappedBy == null)
						getBaseRepository().save(entity);
				} else
					getBaseRepository().save(entity);
			}
            // Reset the entities, it associates to the entity added and save the associations
			if (!liste.isEmpty()) {
				// ONE_TO_ONE
				liste.stream().filter(e -> e.getAssociation().equals(ASSOCIATION_ENUM.ONE_TO_ONE))
						.forEach(e -> {
							e.getList().forEach(l -> UtilsReflection.callSetter(l, e.getMappedBy(), entity));
							if (e.isIdNull())
								UtilsReflection.callSetter(entity, e.getFieldName(), e.getList().get(0));
						});
				// MANY_TO_ONE
				liste.stream().filter(e -> e.getAssociation().equals(ASSOCIATION_ENUM.MANY_TO_ONE))
						.forEach(e -> {
							//e.getList().forEach(l -> UtilsReflection.callSetter(l, e.getMappedBy(), entity));
							if (e.isIdNull())
								UtilsReflection.callSetter(entity, e.getFieldName(), e.getList().get(0));
						});
				// ONE_TO_MANY
				liste.stream().filter(e -> e.getAssociation().equals(ASSOCIATION_ENUM.ONE_TO_MANY))
						.forEach(e -> {
							e.getList().forEach(l -> UtilsReflection.callSetter(l, e.getMappedBy(), entity));
							if (e.isIdNull())
								UtilsReflection.callSetter(entity, e.getFieldName(), e.getList());

							// Delete old entities
							if (entity.getClass().getSuperclass() != null && entity instanceof BaseOriginalEntity) {
								BaseOriginalEntity<T, ID> orgEntity = (BaseOriginalEntity<T, ID>) entity;
								T originalEntity = orgEntity.getOriginalObj();
								//T originalEntity = (T) UtilsReflection.callGetterSuperclass(entity, "originalObj");
                                if (originalEntity != null) {
                                    List<T> originalEntityList = (List<T>) UtilsReflection.callGetter(originalEntity, e.getFieldName());
                                    if (!e.getList().isEmpty() || !originalEntityList.isEmpty()) {
                                        Class<?> repoClass = !e.getList().isEmpty() ? e.getList().get(0).getClass() : originalEntityList.get(0).getClass();
                                        Class<?> idClass = !e.getList().isEmpty() ? e.getList().get(0).getId().getClass() : originalEntityList.get(0).getId().getClass();
                                        BaseRepository<T, ID> baseDynamicRepository = ApplicationContextProvider.getBeanWithGenerics(BaseRepository.class, List.of(repoClass, idClass));
                                        originalEntityList.stream().filter(oElem -> e.getList().stream().noneMatch(aElem -> aElem.getId().equals(oElem.getId())))
                                                .forEach(oElem -> baseDynamicRepository.deleteById(oElem.getId()));
                                    }
                                }
							}
						});
				// MANY_TO_MANY
				liste.stream().filter(e -> e.getAssociation().equals(ASSOCIATION_ENUM.MANY_TO_MANY))
						.forEach(e -> {
							List<T> tempList = new ArrayList<>();
							tempList.add(entity);
							e.getList().forEach(l -> UtilsReflection.callSetter(l, e.getMappedBy(), tempList));
							if (e.isIdNull())
								UtilsReflection.callSetter(entity, e.getFieldName(), e.getList());
						});

				if (embeddedId == null || prevMappedBy == null)
					getBaseRepository().save(entity);
			}
		}
		return entity.getId();
	}

	@Override
	@Transactional
	public T update(T entity) {
		T oldNewEntity = null;
		ID id = entity.getId();
		if (id != null)
			oldNewEntity = update(id, entity);
		return oldNewEntity;
	}

	@Override
	@Transactional
	public T update(ID id, T entity) {
		T oldNewEntity = null;
		String idFieldName = UtilsReflection.findAndGetIdFieldName(entity);
		List<Field> embField = UtilsReflection.getAnnotatedFields(entity.getClass(), EmbeddedId.class);
		Optional<T> oldEntityOpt = getBaseRepository().findById(id);
		if (oldEntityOpt.isPresent()) {
			oldNewEntity = oldEntityOpt.get();
			List<String> names = UtilsReflection.getNullPropertyNamesList(entity);
			BeanUtils.copyProperties(entity, oldNewEntity, names.toArray(new String[0]));
			if (oldNewEntity.getId() == null && embField.isEmpty()) {
				UtilsReflection.callSetter(oldNewEntity, idFieldName, id);
			}
			saveUpdateListsMultiRecursive(oldNewEntity, null);
//			updateListsRecursive(oldNewEntity, null, null, false); //Setta il valore interno di una fk se null
			getBaseRepository().save(oldNewEntity); // Ci vuole altrimenti non salva le liste
		}
		return oldNewEntity;
	}

	@Override
	public boolean updateListsRecursive(T entity, T oldEntity, String mappedBy, boolean recursive){
		List<Field> fields = UtilsReflection.getAnnotatedFields(entity.getClass(), List.of(OneToOne.class, OneToMany.class, ManyToMany.class));
		if (recursive) { // No fields to check? then return
			if (UtilsReflection.callGetter(entity, mappedBy) == null)
				UtilsReflection.callSetter(entity, mappedBy, oldEntity);
			return true;
		}
		else {
			for (Field field : fields) {
				Recursive recursiveAnnotation = field.getAnnotation(Recursive.class);  // Check FK recursive fields
				OneToOne oneToOneAnnotation = field.getAnnotation(OneToOne.class); // TODO
				ManyToMany manyToManyAnnotation = field.getAnnotation(ManyToMany.class); // TODO
				OneToMany oneToManyAnnotation = field.getAnnotation(OneToMany.class);
				List<T> entityList = (List<T>) UtilsReflection.callGetter(entity, field.getName());
				for (T nextEntity : entityList) {
					updateListsRecursive(nextEntity, entity, oneToManyAnnotation.mappedBy(), recursiveAnnotation!=null);
				}
				if (oldEntity != null && UtilsReflection.callGetter(entity, mappedBy) == null) {
                    UtilsReflection.callSetter(entity, mappedBy, oldEntity);
                }
			}
		}
		return true;
	}

	@Override
	@Transactional
	public T patch(T entity, String...fields) {
		T oldNewEntity = null;
		ID id = entity.getId();
		if (id != null)
			oldNewEntity = patch(id, entity, fields);
		return oldNewEntity;
	}

	@Override
	@Transactional
	public T patch(ID id, T entity, String...fields) {
		T oldNewEntity = null;
		Optional<T> oldEntityOpt = getBaseRepository().findById(id);
		if (oldEntityOpt.isPresent()) {
			oldNewEntity = oldEntityOpt.get();
			if (fields.length > 0)
				patch(oldNewEntity, entity, fields);
			else
				patch(oldNewEntity, entity);
		}
		return oldNewEntity;
	}

	@Override
	public T patch(T newEntity, T oldEntity, String...fields) {
		Arrays.stream(fields).forEach(field -> UtilsReflection.callSetter(newEntity, field, UtilsReflection.callGetter(oldEntity, field)));
		getBaseRepository().save(newEntity); // Otherwise it doesn't save the lists
		return newEntity;
	}

	@Override
	public T patch(T newEntity, T oldEntity) {
		// patch values in the dedicated subclasses
		return newEntity;
	}

	@Override
	public T findByNaturalId(Object naturalId) {
		T entity = null;
		Optional<Field> field = UtilsReflection.getFirstAnnotatedField(type, NaturalId.class);
		if (field.isPresent()) {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> query = cb.createQuery(type);
			Root<T> root = query.from(type);
			query.select(root)
					.where(cb.equal(root.get(field.get().getName()), naturalId));
			TypedQuery<T> typedQuery = entityManager.createQuery(query);
			entity = typedQuery.getSingleResult();
		}
		return entity;
	}

	@Override
	public Page<T> findAll(int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return getBaseRepository().findAll(paging);
	}
}
