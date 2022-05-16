package constants;

public class Constants {

    public static class Variables{

        public static String Sid="220516pbs1luslow2r50";
        public static String AdrId="42933175";
        public static String AddrTypeCi="CI";
        public static String ExtAddrId="1000095894";
        public static String ExtAddrType="JI";
        public static String Event_VBR="125894";
        public static String Ldap="DN301182NTI";
        public static String State="WT";
        public static String Sourse="NHC";
        public static String Lang="UA";
        public static String Bank="PB";
        public static String Lg="DN301182NTI";
        public static String Ch_VBR="VBR";
        public static int StatusCode=200;
        public static String InitialState="IN";
        public static String status = "delivered";
        public static String priority="1";
        //public static String Timestamp ="20200401173847";


        public static String ref;
        public static String ref1;
        public static String Timestamp;
        public static String Timestamp1;
        public static String stateS;

        public static String server = Servers.Base_URL;
        public static String path = Path.AddEvent_PATH;

        public static String DB_URL = "jdbc:sybase:Tds:10.56.128.121:5000/Contact";
        public static String USER = "dn301182nti";
        public static String PASS = "14789632g";
    }

    public static class Servers{
        public static String Base_URL="http://test.crm.it.loc:2381/Contact/";
        public static String WhpPUSH_URL="http://test.crm.it.loc:6550/whppush/api/";
    }

    public static class Path{
        public static String AddEvent_PATH="api/";
        public static String WhpPUSH_PATH="vbr/smscallback";
           }

    public static class Actions{
        public static String AddEvent_ADD="event/add";
        public static String ModifyEvent="event/modify";
        public static String WhpPUSH_act = "?"+ Variables.ref1;
        //public static String ModifyEvent="EventTypeAttr";//стало вместо event/modify

    }

}
