
import java.util.ArrayList;
import java.util.List;

public class CompareAwsS3Objects {

    List<String> QAfieldNames;
    List<String> QAdataTypes;
    List<String> QAlength;
    List<String> QArequired;

    List<String> ProdfieldNames;
    List<String> ProddataTypes;
    List<String> Prodlength;
    List<String> Prodrequired;

    String message;

    public CompareAwsS3Objects(String objectName, String namespace, String usage, String fileType,String timeStamp) throws Exception {

        ParseXMLForFields qa = new ParseXMLForFields(objectName, namespace, usage, fileType,"qa");
        QAfieldNames = qa.getFieldNames();
        QAdataTypes = qa.getTypes();
        QAlength = qa.getLengths();
        QArequired = qa.getRequired();

        ParseXMLForFields Prod=new ParseXMLForFields(objectName, namespace, usage, fileType,"prod");
        ProdfieldNames = Prod.getFieldNames();
        ProddataTypes =Prod.getTypes();
        Prodlength =Prod.getLengths();
        Prodrequired =Prod.getRequired();
        message = "\n"+objectName+"\n";
        System.out.println("\n"+objectName);
        compare(QAfieldNames,ProdfieldNames);
        new WriteToFile(message,"/tmp/report_"+timeStamp+".txt",true).write();
    }

    public void compare(List<String> QA, List<String> Prod)
    {
        List<String> QAFields = new ArrayList<>(QA);
        List<String> ProdFields = new ArrayList<>(Prod);

        QAFields.removeAll(Prod);
        ProdFields.removeAll(QA);

        if(QAFields.size()!=0 || ProdFields.size()!=0) {
            message = message + "\n:( bad news!!!\n";
            message = message + "\nFields that exist in QA and not in Prod:\n" + QAFields;
            message = message + "\nFields that exist in Prod and not in QA:\n" + ProdFields;
            System.out.println("\n:( bad news!!!\n"+
                                "\nFields that exist in QA and not in Prod:\n" + QAFields+
                                "\nFields that exist in Prod and not in QA:\n" + ProdFields);
            compareFurther();

        }

        else {
            message = message + "\n:) good news!!! FieldNames Match!!! \n\nFurther comparison going on...";
            System.out.println("\n:) good news!!! FieldNames Match!!! \n\nFurther comparison going on...");
            compareFurther();
        }

    }

    public void compareFurther()
    {
        int flag=0;
        for(int i=0;i<QAfieldNames.size();i++)
        {
            for( int j=0;j<ProdfieldNames.size();j++)
            {
                if(QAfieldNames.get(i).equals(ProdfieldNames.get(j)))
                {
                    if(QAdataTypes.get(i).equalsIgnoreCase(ProddataTypes.get(j)) && QAlength.get(i).equals(Prodlength.get(j))
                            && QArequired.get(i).equalsIgnoreCase(Prodrequired.get(j)))
                    {
                        //do nothing
                    }
                    else
                    {
                        message = message + "\n"+QAfieldNames.get(i)+" does not match";
                        message = message + "\nQA datatype....."+QAdataTypes.get(i)+"     Prod datatype....."+ProddataTypes.get(j);
                        message = message + "\nQA length....."+QAlength.get(i)+"     Prod length....."+Prodlength.get(j);
                        message = message + "\nQA required....."+QArequired.get(i)+"     Prod required....."+Prodrequired.get(j);
                        System.out.println("\n"+QAfieldNames.get(i)+" does not match"
                                + "\nQA datatype....."+QAdataTypes.get(i)+"     Prod datatype....."+ProddataTypes.get(j)
                                + "\nQA length....."+QAlength.get(i)+"     Prod length....."+Prodlength.get(j)
                                + "\nQA required....."+QArequired.get(i)+"     Prod required....."+Prodrequired.get(j));
                        flag=1;
                    }

                }
            }
        }

        if(flag==1) {
            message = message + "\n:( bad news!!!";
            System.out.println("\n:( bad news!!!");
        }
        else {
            message = message + "\n:) good news!!! dataType, length and required also matches!!!";
            System.out.println("\n:) good news!!! dataType, length and required also matches!!!");
        }

    }

}
