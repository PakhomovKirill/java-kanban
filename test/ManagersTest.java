import static org.junit.jupiter.api.Assertions.*;

import manager.Managers;
import manager.history.HistoryManager;
import org.junit.jupiter.api.Test;

class ManagersTest {
    private static Managers manager = new Managers();
    private static HistoryManager HM = manager.getDefaultHistory();
    private static HistoryManager tm = manager.getDefaultHistory();

    @Test
    public void IsManagerClassesIsExist() {
        assertNotNull(HM, "Возвращаемое значение не должно ровняться null");
        assertNotNull(tm, "Возвращаемое значение не должно ровняться null");
    }
}