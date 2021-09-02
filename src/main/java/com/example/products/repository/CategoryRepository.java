package com.example.products.repository;

import com.example.products.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String>, CustomRepository {

    @Modifying( clearAutomatically = true )
    @Query( "UPDATE Category c SET c.status = 'DELETED' WHERE c.id = :id AND " +
            "(SELECT COUNT(p) FROM Product p WHERE p.category.id = :id AND p.status = 'ACTIVE') = 0" )
    void deleteById ( @Param( "id" ) String id );
}
