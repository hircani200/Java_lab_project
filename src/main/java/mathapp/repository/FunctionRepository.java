package mathapp.repository;

import mathapp.model.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionEntity, Long> {
    List<FunctionEntity> findByName(String name);
    List<FunctionEntity> findByNameContainingIgnoreCase(String name);
    Object findByXFromBetween(double value1, double value2);
}