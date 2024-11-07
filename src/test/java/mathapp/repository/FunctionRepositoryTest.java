package mathapp.repository;

import mathapp.model.FunctionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FunctionRepositoryTest {

    @Autowired
    private FunctionRepository functionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    @Transactional
    public void setUp() {
        entityManager.createQuery("DELETE FROM FunctionEntity").executeUpdate();
        FunctionEntity functionEntity = new FunctionEntity();
        functionEntity.setType("Test Function");
        functionRepository.save(functionEntity);
    }

    @Test
    @Rollback(value = true)
    public void testFindByType() {
        List<FunctionEntity> functions = functionRepository.findByType("Test Function");
        assertEquals(1, functions.size());
    }
}

