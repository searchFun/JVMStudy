package concurrent.homework.log;


/**
 * ��־����������������Ҫ��־��¼�Ķ�ʹ�ô˹�����ȡ
 *
 * @author wanglw2
 */
public class LoggerTest {
    /**
     * ��ȡ��־��¼�Ĺ�������
     *
     * @param clazz
     * @return
     */
    public static ILog getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }


    public static void main(String[] a) {
        try{
            throw new NullPointerException(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        String usercode = "����";
        //���κ����壬�Է�������������κΰ���
        System.out.println("------------------------------------------");
        //��־��¼Ӧʹ��ͳһ�Ľӿڣ����ܶ��ַ�������ֱ��ƴ��
        System.out.println("��ʼ�����û���[" + usercode + "]��н��");
        System.out.println();
        System.out.println();
        System.out.println();
        ILog log = getLogger(LoggerTest.class);
        log.debug("��ʼ�����û���[{}]��н��", usercode); //׼ȷ
        log.info("��ʼ�����û���[" + usercode + "]��н��"); //����
        log.warn("��ʼ�����û���[" + usercode + "]��н��"); //����


        try {
            throw new NullPointerException("null");
        } catch (Exception e) {
            //������Ҫʹ��ͳһ�Ľӿ�
            e.printStackTrace();
            System.out.println("--1--");
            //���󣬶�ʧ�쳣��ջ��Ϣ
            log.error(e.getMessage());
            System.out.println("--2--");
            //���󣬶�ʧ�쳣��ջ��Ϣ
            log.error("error", e.getMessage());
            System.out.println("--3--");
            //��ȷ
            log.error("error", e);
        }

    }
}
