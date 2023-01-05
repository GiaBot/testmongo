package com.example.demo.DTOs;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.Repository.CustomerRepository;
import com.example.demo.models.Customer;

public class CustomerDTO implements CustomerRepository{

    CustomerRepository repository;

    @Override
    public <S extends Customer> S insert(S entity) {
        return repository.insert(entity);
    }

    @Override
    public <S extends Customer> List<S> insert(Iterable<S> entities) {
        return repository.insert(entities);
    }

    @Override
    public <S extends Customer> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends Customer> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public <S extends Customer> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public List<Customer> findAllById(Iterable<String> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Optional<Customer> findById(String id) {
        Optional<Customer> customer = repository.findById(id);
        if(customer == null) return Optional.empty();
        return customer;
    }

    @Override
    public boolean existsById(String id) {
        Optional<Customer> customer = repository.findById(id);
        if(customer == null) return false;
        return true;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);        
    }

    @Override
    public void delete(Customer entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        repository.deleteAllById(ids);        
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        repository.deleteAll(entities);        
    }

    @Override
    public List<Customer> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Customer> Optional<S> findOne(Example<S> example) {
        Optional<S> customer = repository.findOne(example);
        if(customer == null) return Optional.empty();
        return customer;
    }

    @Override
    public <S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <S extends Customer> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public <S extends Customer> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    public <S extends Customer, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        return repository.findBy(example, queryFunction);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public <S extends Customer> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
    
}
