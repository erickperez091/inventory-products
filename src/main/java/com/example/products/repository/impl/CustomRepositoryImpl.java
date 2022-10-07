package com.example.products.repository.impl;

import com.example.products.entity.Category;
import com.example.products.entity.Product;
import com.example.products.repository.CustomRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional< Product > findProductById( String id ) {
        Query query = entityManager.createQuery( "SELECT p FROM Product p WHERE p.id = :id", Product.class );
        query.setParameter( "id", id );
        enableFilter( "productActive" );
        Object result = query.getResultList().stream().findFirst();
        return (Optional< Product >) result;
    }

    @Override
    public Optional< Category > findCategoryById( String id ) {
        Query query = entityManager.createQuery( "SELECT c FROM Category c WHERE c.id = :id", Category.class );
        query.setParameter( "id", id );
        enableFilter( "categoryActive" );
        Object result = query.getResultList().stream().findFirst();
        return (Optional< Category >) result;
    }

    @Override
    public Optional< List< Product > > findProductsByCategory( String categoryId ) {

        Query query = entityManager.createQuery( "SELECT p FROM Product p " +
                "INNER JOIN p.category c WHERE c.id = :category_id AND c.status = 'ACTIVE'", Product.class );
        query.setParameter( "category_id", categoryId );
        enableFilter( "productActive" );
        List< Product > result = (List< Product >) query.getResultList();
        return Optional.of( result );
    }

    private void enableFilter( String filterName ) {
        if ( Objects.nonNull( entityManager ) ) {
            Session session = entityManager.unwrap( Session.class );
            session.enableFilter( filterName );
        }
    }
}
