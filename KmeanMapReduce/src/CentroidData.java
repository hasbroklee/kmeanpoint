import java.util.List;
import java.util.Map;

public class CentroidData {
    public PointWritable[] centroids;
    public Map<Integer, List<PointWritable>> pointsPerCentroid;

    public CentroidData(PointWritable[] centroids, Map<Integer, List<PointWritable>> pointsPerCentroid) {
        this.centroids = centroids;
        this.pointsPerCentroid = pointsPerCentroid;
    }
}
