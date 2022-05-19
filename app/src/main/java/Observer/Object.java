package Observer;

import java.util.List;

public interface Object{
     void addObserver(List<? extends Observer> observer);
    <T> void removeObserver(T observer);
    int notifyAllObserver();
}
