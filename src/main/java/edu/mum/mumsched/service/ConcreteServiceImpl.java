package edu.mum.mumsched.service;

import edu.mum.mumsched.model.User;
import edu.mum.mumsched.repository.BaseUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Service("concreteService")
public abstract class ConcreteServiceImpl<TEntity extends User, TModel> implements UserService<TEntity, TModel> {

    @Autowired
    private BaseUserRepository<TEntity> baseUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    protected Class<TEntity> entityClass;
    protected Class<TModel> modelClass;

    @SuppressWarnings("unchecked")
    public ConcreteServiceImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<TEntity>) genericSuperclass.getActualTypeArguments()[0];
        this.modelClass = (Class<TModel>) genericSuperclass.getActualTypeArguments()[1];
    }

    public TModel findOne(Integer id) {
        TEntity entity = baseUserRepository.findOne(id);
        return modelMapper.map(entity, modelClass);
    }

    public List<TModel> findAll() {
        List<TModel> result = new ArrayList<>();
        List<TEntity> entities = baseUserRepository.findAll();
        for (TEntity entity : entities) {
            result.add(modelMapper.map(entity, modelClass));
        }
        return result;
    }

    public void update(TModel model) {
        TEntity entity = modelMapper.map(model, entityClass);
        baseUserRepository.saveAndFlush(entity);
    }

    public void delete(Integer id) {
        TEntity entity = baseUserRepository.findOne(id);
        baseUserRepository.saveAndFlush(entity);
    }

    public void save(TModel model) {
        TEntity entity = modelMapper.map(model, entityClass);
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        entity.setActive(true);
        baseUserRepository.saveAndFlush(entity);
    }

    public TModel findByEmail(String email) {
        TEntity entity = baseUserRepository.findByEmail(email);
        return modelMapper.map(entity, modelClass);
    }
}
