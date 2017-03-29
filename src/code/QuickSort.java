package code;

/**
 * Created by Soli on 26/03/2017.
 */
public class QuickSort {

public static void quickSortConnectedCacheServers(ConnectedCacheServer[] connectedCacheServers, int low, int high) {
        if (connectedCacheServers == null || connectedCacheServers.length == 0)
            return;

        if (low >= high)
            return;

        // pick the pivot
        int middle = low + (high - low) / 2;
        int pivot = connectedCacheServers[middle].latency;

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            while (connectedCacheServers[i].latency < pivot) {
                i++;
            }

            while (connectedCacheServers[j].latency > pivot) {
                j--;
            }

            if (i <= j) {
                ConnectedCacheServer temp = connectedCacheServers[i];
                connectedCacheServers[i] = connectedCacheServers[j];
                connectedCacheServers[j] = temp;
                i++;
                j--;
            }
        }

        // recursively sort two sub parts
        if (low < j)
            quickSortConnectedCacheServers(connectedCacheServers, low, j);

        if (high > i)
            quickSortConnectedCacheServers(connectedCacheServers, i, high);
    }
    public static void quickSortVideos(Video[] videos, int low, int high) {
        if (videos == null || videos.length == 0)
            return;

        if (low >= high)
            return;

        // pick the pivot
        int middle = low + (high - low) / 2;
        double pivot = videos[middle].value;

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            while (videos[i].value < pivot) {
                i++;
            }

            while (videos[j].value > pivot) {
                j--;
            }

            if (i <= j) {
                Video temp = videos[i];
                videos[i] = videos[j];
                videos[j] = temp;
                i++;
                j--;
            }
        }

        // recursively sort two sub parts
        if (low < j)
            quickSortVideos(videos, low, j);

        if (high > i)
            quickSortVideos(videos, i, high);
    }
}