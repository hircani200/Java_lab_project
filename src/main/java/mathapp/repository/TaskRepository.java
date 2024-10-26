package mathapp.repository;

import mathapp.model.FunctionEntity;
import mathapp.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByFunction(FunctionEntity function);
}