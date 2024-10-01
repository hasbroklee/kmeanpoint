import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class KReducer extends Reducer<LongWritable, PointWritable, Text, Text> {

    private final Text newCentroidId = new Text();
    private final Text newCentroidValue = new Text();

    public void reduce(LongWritable centroidId, Iterable<PointWritable> partialSums, Context context)
            throws IOException, InterruptedException {

        PointWritable ptFinalSum = null;
        boolean isFirst = true;

        List<String> pointsList = new ArrayList<>();
        for (PointWritable partialSum : partialSums) {

            if (isFirst) {
                ptFinalSum = PointWritable.copy(partialSum);
                isFirst = false;
            } else {
                ptFinalSum.sum(partialSum);  
            }

            pointsList.add(partialSum.toString());
        }


        if (ptFinalSum != null) {
            ptFinalSum.calcAverage();
        }


        newCentroidId.set("Centroid: " + centroidId.toString());
        StringBuilder outputValue = new StringBuilder();
        outputValue.append("New Centroid: ").append(ptFinalSum.toString()).append(" | Points: [");

        for (int i = 0; i < pointsList.size(); i++) {
            outputValue.append(pointsList.get(i));
            if (i != pointsList.size() - 1) {
                outputValue.append(", ");
            }
        }
        outputValue.append("]");

        newCentroidValue.set(outputValue.toString());
        context.write(newCentroidId, newCentroidValue);
    }
}
