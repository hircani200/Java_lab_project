package mathapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "functions")
public class FunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long functionId;

    private String type;
    private Double xFrom;
    private Double xTo;
    private Integer count;

    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FunctionPointEntity> points;

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getXFrom() {
        return xFrom;
    }

    public void setXFrom(Double xFrom) {
        this.xFrom = xFrom;
    }

    public Double getXTo() {
        return xTo;
    }

    public void setXTo(Double xTo) {
        this.xTo = xTo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<FunctionPointEntity> getPoints() {
        return points;
    }

    public void setPoints(List<FunctionPointEntity> points) {
        this.points = points;
    }
}