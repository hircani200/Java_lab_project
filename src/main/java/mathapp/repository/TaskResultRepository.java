package mathapp.repository;

import mathapp.model.TaskEntity;
import mathapp.model.TaskResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskResultRepository extends JpaRepository<TaskResultEntity, Long> {
    TaskResultEntity findByTask(TaskEntity task);
}