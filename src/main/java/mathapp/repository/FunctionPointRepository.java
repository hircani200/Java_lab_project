package mathapp.repository;

import mathapp.model.FunctionEntity;
import mathapp.model.FunctionPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionPointRepository extends JpaRepository<FunctionPointEntity, Long> {
    List<FunctionPointEntity> findByFunction(FunctionEntity function);
}