package tests;

import static org.junit.jupiter.api.Assertions.*;

import manager.Managers;
import manager.history.InMemoryHistoryManager;
import manager.task.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

class ManagersTest {
    private static Managers Manager = new Managers();
    private static InMemoryHistoryManager HM = Manager.getDefaultHistory();
    private static InMemoryTaskManager TM = Manager.getDefaultClass();

    @Test
    public void IsManagerClassesIsExist(){
        assertNotNull(HM, "Возвращаемое значение не должно ровняться null");
        assertNotNull(TM, "Возвращаемое значение не должно ровняться null");
    }
}