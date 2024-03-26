import static org.junit.jupiter.api.Assertions.*;

import manager.Managers;
import manager.history.HistoryManager;
import org.junit.jupiter.api.Test;

class ManagersTest {
    private static Managers Manager = new Managers();
    private static HistoryManager HM = Manager.getDefaultHistory();
    private static HistoryManager TM = Manager.getDefaultHistory();

    @Test
    public void IsManagerClassesIsExist(){
        assertNotNull(HM, "Возвращаемое значение не должно ровняться null");
        assertNotNull(TM, "Возвращаемое значение не должно ровняться null");
    }
}