/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchMsag;

//import java.awt.Component;


import java.awt.Color;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author tamiru.mollah
 */
public class searchJFrame extends javax.swing.JFrame{
    public int rSrchCount,rSrchIndex,searchIndex=-1;
    public int count=0;
    
    public ArrayList<String> deleteIpHost=new ArrayList<>();
    public int vCount=0;
    public int findCount=0,vlanCount=0,ipCount=0;
    public int findC=0;
    public int ipRangeIndexer=0;
    public ArrayList<String> helpData;
    public java.sql.Date cDate=new java.sql.Date(System.currentTimeMillis());
    public String activity="";
    public ArrayList<String> iphostData=new ArrayList<>();
    public ArrayList<String> loadedData=new ArrayList<>();
    public ArrayList<String> updatedData=new ArrayList<>();
    public ArrayList<String> iphostDataA=new ArrayList<>(); 
    public ArrayList<String> allVlanData;
    public ArrayList<String> OTR=new ArrayList<>();
    public ArrayList <Integer> findIndex;
    public ArrayList<String> allData=new ArrayList<>(); 
    public ArrayList<String>[][] allIpData;
    public ArrayList<String>[][] takenIpData;
    public ArrayList<String>[][] freeIpData;
    public ArrayList<String>[] takenVlanData;
    public ArrayList<String> freeVlanData=new ArrayList<>();
    public String [][] A;
    public String [][] oltTt;public String [][] brData;public String [][] erData;
    public String [][] IPrange;public String [][] ipAllocationData;public String [][] swData;
    public String macIn;   
    public int rows,rowsOltTt,rowsIPrange;    
    public int cols,colsOltTt,colsIPrange;
    public int pCount=0;
    
    /**
     * Creates new form searchFrame
     * 
     * @param <error>     
     * @param sub
     * @return 
     */
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@
     public String [][] xlRead(String fileName,String sheetName) {    
	 int roww,coll;	 
	 HSSFRow row;
         HSSFCell cell;
         String [][] XLA; 
		 
        FileInputStream file = null;        
        try {
            file = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(searchJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
         HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(file);
        } catch (IOException ex) {
            Logger.getLogger(searchJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         Sheet sheet = wb.getSheet(sheetName);
         roww=sheet.getPhysicalNumberOfRows();
         coll=sheet.getRow(0).getPhysicalNumberOfCells();        
         XLA=new String[roww][coll];
    
         for (int i=0;i<roww;i++){
             row =(HSSFRow) sheet.getRow(i);
             for (int j=0;j<coll;j++){
              cell= row.getCell(j); 
              cell.setCellType(Cell.CELL_TYPE_STRING);
               XLA[i][j]=String.valueOf(cell.getStringCellValue());              
             }
             
         }		 
 return XLA;
}
     //@@@@@@@@@@@@@@@@@@@@@@@@@@
    public void updateTask(ArrayList<String> ss){
        iphostDataA=new ArrayList<>();  
        iphostDataA=new ArrayList(iphostData);      
         ArrayList <String> updateData=xmlListRead("updatedTask");       
        for(String a:ss) updateData.add(a);       
        xmlListWrite(updateData,"updatedTask");     
        for(String mike:xmlListRead("updatedTask"))iphostDataA.add(mike);
         for(String mike:xmlListRead("updatedTask"))updatedData.add(mike); 
         dataUpdater(updateData);
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@@@
    public void updateTask(String ss){ 
        
        iphostDataA=new ArrayList<>();  
        iphostDataA=new ArrayList(iphostData);  
        ArrayList<String> updateData=xmlListRead("updatedTask");       
        String[] A=ss.split("\n");
        for(String a:A) updateData.add("  "+a);       
        xmlListWrite(updateData,"updatedTask");     
        for(String mike:xmlListRead("updatedTask"))iphostDataA.add(mike); 
         for(String mike:xmlListRead("updatedTask"))updatedData.add(mike); 
        dataUpdater(updateData);
    }
    
       //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList<String> numSplit(String input){
    
    String[] A=input.split("[a-zA-Z]+");
        if(A.length>0) return new ArrayList(Arrays.asList( A));       
        else   return new ArrayList(Arrays.asList( new String[]{""}));
    
    }
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList<String>  strSplit(String input){
    String[] A=input.trim().split("\\s+");
    
    return new ArrayList(Arrays.asList(A)); 
    
    }
      //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public String  arrayList2Str(ArrayList<String> input){
    String A="";
    for(String B:input) A=A+"\n"+B;
    
    return A; 
    
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean checkIpRange(String wan){
        BigInteger WanIp;
        String range="",wanIpArea="",ipAlloc,sIP,eIP;
        int rIndex=-1,X=0,noHost,sub;       
        int ipRangeIndex=findIndex(dataColls(IPrange,0),A[searchIndex][17]); 

     for (int i = 1; i < IPrange[0].length-1; i++) {       
         
          range=IPrange[ipRangeIndex][i];
          rIndex= range.indexOf("-");
          sIP=range.substring(0,rIndex); eIP=range.substring(rIndex+1,range.length());      
          noHost=(subIp(eIP,1)-subIp(sIP,1)+1)*(subIp(eIP,2)-subIp(sIP,2)+1)*(subIp(eIP,3)-subIp(sIP,3)+1)*(subIp(eIP,4)-subIp(sIP,4)+3);
          sub=1+(Integer.numberOfLeadingZeros(noHost));
         if(indexOfSymbol(wan,".").size()==3)
          if(networkIp(sIP,sub).compareTo(networkIp(wan,sub))==0) X=1;
       
        }
         if( X==1 )return true;else return false;
        
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList  findAllData( String srch){
 ArrayList<String> iphostData1=iphostDataA;
     ArrayList<Integer> findAllIndex=new ArrayList<>();	 
     ArrayList<String> allData=new ArrayList<>();    
     findAllIndex= searchIndex(iphostData1,srch);
    
  if(!(findAllIndex.isEmpty())) {
        for(int A:findAllIndex){
        int fIndex=A;   
           
       if(!(iphostData1.get(fIndex).contains("ip host")||iphostData1.get(fIndex).contains("ip-host")||iphostData1.get(fIndex).contains("ip route"))){
           int initiaL=-1; int finaL=-1;
           for (int i =fIndex ; i < iphostData1.size(); i++) {
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      finaL=i;break;
                  }                  
           }
            for (int i =fIndex ; i >=0; i--) {
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      initiaL=i;break;}
                   }
            
           if(initiaL>-1&&finaL>0) for (int i = initiaL; i <=finaL; i++)allData.add(iphostData1.get(i));
                          
        }
       }         
     }
       
return allData;	 
}
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
public ArrayList<String> freeVlanFinder(ArrayList<String> DATA ,String exVlan){
    
  ArrayList<String> data= new ArrayList<>(); 
  ArrayList<Integer> findAllIndex= new ArrayList<>(); 
   ArrayList<String> vpnData= new ArrayList<>(); 
  ArrayList<String> d= new ArrayList<>();  
  
  String bbInt="#$",bbInt17="#$",bbInt200="#$",mVlan,msVlan,pInt="",pVlan,pExVlan,vpnInt="",vpnVlan="",vpnExVlan;
  vpnInt=A[searchIndex][6];   
 if(A[searchIndex][4].contains("/")){
      String mm=A[searchIndex][4].trim();
      int ind=mm.indexOf("/");     
      bbInt="slot "+mm.substring(0,ind)+" port "+mm.substring(ind+1,mm.length());
//      bbInt17=bbInt; bbInt200=bbInt;
       pInt="gei_"+A[searchIndex][4]; 
  }
  if(!((A[searchIndex][4].contains("/")))){
    bbInt="smartgroup"+A[searchIndex][4];
//    bbInt17="smartgroup"+A[searchIndex][4]+"."+A[searchIndex][9];
//    bbInt200="smartgroup"+A[searchIndex][4]+"."+A[searchIndex][16];
    pInt="smartgroup"+A[searchIndex][4]+"."; 
    }
  
       ArrayList<String> allVlan=new ArrayList<>();
       for (int j = 1001; j < 1601; j++) {
          allVlan.add(""+j); 
       }
  
 
   for(int i=0;i<DATA.size();i++){
       String name=DATA.get(i);
     
       /////////////////////////////////   blocked ip host vlan
         if((name.contains("ip host"))&&(name.contains("vlan"))&&(name.contains("up-rate"))&&(!(name.contains("second-vlan")))&&
            (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){
       d=bbParamFind(name); 
      String blockName=d.get(d.size()-1).trim();   
      String blockVlan=blockName.substring(blockName.length()-4,blockName.length());       
      String VVlan=numSplit(blockVlan).get(numSplit(blockVlan).size()-1);
        if(name.contains("smartgroup")){
            mVlan=d.get(2); 
           
        }else {
                mVlan=d.get(4);
                
                } 
     if(mVlan.equals(A[searchIndex][2])) {  
        data.add(VVlan); 
        continue;
     }    
    }
       //////////////////////////
    if((name.contains("ip-host"))&&(name.contains("vlan"))&&(name.contains("author-temp-name"))&&(!(name.contains("sec-vlan")))&&
            (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){
       d=bbParamFind(name); 
      String blockName=d.get(d.size()-1).trim();   
      String blockVlan=blockName.substring(blockName.length()-4,blockName.length());       
      String VVlan=numSplit(blockVlan).get(numSplit(blockVlan).size()-1);
        if(name.contains("smartgroup")){
            mVlan=d.get(2); 
           
        }else {
                mVlan=d.get(4);
                
                } 
     if(mVlan.equals(A[searchIndex][2])) {  
        data.add(VVlan); 
        continue;
     }    
    }
   /////////////////////////////////    
//       System.out.println(name.contains(bbInt)||name.contains(bbInt17)||name.contains(bbInt200));   
    /////////////////////////////////  active ip host vlan
//    if((name.contains("ip host"))&&(name.contains("vlan"))&&(name.contains("second-vlan"))&&(name.contains("up-rate"))&&
//            (name.contains(bbInt)||name.contains(bbInt17)||name.contains(bbInt200))){
      // System.out.println(name); 
      if((name.contains("ip-host"))&&(name.contains("vlan"))&&(name.contains("sec-vlan"))&&(name.contains("author-temp-name"))&&
            (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){

       d=bbParamFind(name);
        if(name.contains("smartgroup")){
            mVlan=d.get(2); 
            msVlan=d.get(3);
              
        }else {
                mVlan=d.get(4);
                msVlan=d.get(5);
                } 
     if(mVlan.equals(A[searchIndex][2])) {  
       if(!msVlan.equals(null)) {
           data.add(msVlan);
           continue;
       } 
       
     }    
    }
  
   //////////////////////////////////  public IP 

   if((name.contains("internal-vlan "))&&(name.contains("external-vlan "))){
      if(DATA.get(i-1).contains(pInt)) {
          if (DATA.get(i+1).contains("ip address")){
              pExVlan=strSplit(name).get(strSplit(name.trim()).size()-1);
                if(pExVlan.equals(A[searchIndex][2])){
                     if(checkIpRange(midString(DATA.get(i+1),"ip address "," "))){
                          pVlan=midString(name,"internal-vlan","external-vlan");
                          data.add(pVlan); 
                          continue;
        } 
      }
     }
   }
   }
 
     //////////////////////////////////  VPN
   if((name.contains("internal-vlanid"))&&(name.contains("external-vlanid"))){
      if(DATA.get(i-1).contains(vpnInt)) {
         vpnExVlan=strSplit(name).get(strSplit(name.trim()).size()-1);
         if(vpnExVlan.equals(A[searchIndex][2])){
              vpnData=findAllData(DATA.get(i-1).trim());
               findAllIndex= searchIndex(vpnData,"ip address");
                   if(!(findAllIndex.isEmpty())) { 
                      if(checkIpRange(midString(vpnData.get(findAllIndex.get(0)),"ip address "," "))){
                         vpnVlan=midString(name,"internal-vlanid","external-vlanid");
                         data.add(vpnVlan);

         }
        }
       }  
      } 
    }
   }

 return (XOR(allVlan,data));
    
}
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
public ArrayList<String> freeVlanFinder(){
  return XOR(allVlanData,freeVlanData);  
}
// @@@@@@@@@@@@@@@@@@@@@@@2@@@@@@@
public ArrayList takenVlanFinder(ArrayList<String> DATA){
//   ArrayList<String>[] allMsagVlan= new ArrayList[dataLength];
//    for (int c = 0; c < dataLength; c++) {
//        allMsagVlan[c]= new ArrayList<>();
//    }
    ArrayList<String> data= new ArrayList<>(); 
  ArrayList<Integer> findAllIndex= new ArrayList<>(); 
   ArrayList<String> vpnData= new ArrayList<>(); 
  ArrayList<String> d= new ArrayList<>();  
  
  String bbInt="#$",bbInt17="#$",bbInt200="#$",mVlan,msVlan,pInt="",pVlan,pExVlan,vpnInt="",vpnVlan="",vpnExVlan,ipAlloc="";
  
  
       ArrayList<String> allVlan=new ArrayList<>();
       for (int j = 1001; j < 1601; j++) {
          allVlan.add(""+j); 
       }
    // @@@@@@@@@@@@@@
   
  
   vpnInt=A[searchIndex][6];  
   
 if(A[searchIndex][4].contains("/")){
      String mm=A[searchIndex][4].trim();
      int ind=mm.indexOf("/");     
      bbInt="slot "+mm.substring(0,ind)+" port "+mm.substring(ind+1,mm.length());

       pInt="gei_"+A[searchIndex][4]; 
  }
  if(!((A[searchIndex][4].contains("/")))){
    bbInt="smartgroup"+A[searchIndex][4];

    pInt="smartgroup"+A[searchIndex][4]+"."; 
    } 
  ipAlloc=A[searchIndex][17];
    //@@@@@@@@@@@@
 
   for(int i=0;i<DATA.size();i++){
       
   
 
    
       String name=DATA.get(i);
     
       /////////////////////////////////   blocked ip host vlan
         if((name.contains("ip host"))&&(name.contains("vlan"))&&(name.contains("up-rate"))&&(!(name.contains("second-vlan")))&&
            (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){
       
       String INTER=d.get(0);
      String blockName=d.get(d.size()-1).trim();   
      String blockVlan=blockName.substring(blockName.length()-4,blockName.length());       
      String VVlan=numSplit(blockVlan).get(numSplit(blockVlan).size()-1);
        if(name.contains("smartgroup")){
            mVlan=d.get(2); 
           
        }else {
                mVlan=d.get(4);
                
                } 
     if(mVlan.equals(A[searchIndex][2])) {  
        data.add(VVlan); 
//        allMsagVlan[searchIndex].add(VVlan);
//        break;
     }    
    }
       //////////////////////////
    if((name.contains("ip-host"))&&(name.contains("vlan"))&&(name.contains("author-temp-name"))&&(!(name.contains("sec-vlan")))&&
            (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){
       d=bbParamFind(name); 
      String blockName=d.get(d.size()-1).trim();   
      String blockVlan=blockName.substring(blockName.length()-4,blockName.length());       
      String VVlan=numSplit(blockVlan).get(numSplit(blockVlan).size()-1);
        if(name.contains("smartgroup")){
            mVlan=d.get(2); 
           
        }else {
                mVlan=d.get(4);
                
                } 
     if(mVlan.equals(A[searchIndex][2])) {  
        data.add(VVlan); 
//        allMsagVlan[searchIndex].add(VVlan);
//        break;
     }    
    }
   /////////////////////////////////    
//       System.out.println(name.contains(bbInt)||name.contains(bbInt17)||name.contains(bbInt200));   
    /////////////////////////////////  active ip host vlan
//    if((name.contains("ip host"))&&(name.contains("vlan"))&&(name.contains("second-vlan"))&&(name.contains("up-rate"))&&
//            (name.contains(bbInt)||name.contains(bbInt17)||name.contains(bbInt200))){
      // System.out.println(name); 
      if((name.contains("ip-host"))&&(name.contains("vlan"))&&(name.contains("sec-vlan"))&&(name.contains("author-temp-name"))&&
            (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){

       d=bbParamFind(name);
        if(name.contains("smartgroup")){
            mVlan=d.get(2); 
            msVlan=d.get(3);
              
        }else {
                mVlan=d.get(4);
                msVlan=d.get(5);
                } 
     if(mVlan.equals(A[searchIndex][2])) {  
       if(!msVlan.equals(null)) {
           data.add(msVlan);
//           allMsagVlan[searchIndex].add(msVlan);
//           break;
       } 
       
     }    
    }
  
   //////////////////////////////////  public IP 

   if((name.contains("internal-vlan "))&&(name.contains("external-vlan "))){
      if(DATA.get(i-1).contains(pInt)) {
          if (DATA.get(i+1).contains("ip address")){
              pExVlan=strSplit(name).get(strSplit(name.trim()).size()-1);
                if(pExVlan.equals(A[searchIndex][2])){
                     if(checkIpRange(midString(DATA.get(i+1),"ip address "," "))){
                          pVlan=midString(name,"internal-vlan","external-vlan");
                          data.add(pVlan); 
//                          allMsagVlan[searchIndex].add(pVlan);
//                          break;
        } 
      }
     }
   }
   }
 
     //////////////////////////////////  VPN
   if((name.contains("internal-vlanid"))&&(name.contains("external-vlanid"))){
      if(DATA.get(i-1).contains(vpnInt)) {
         vpnExVlan=strSplit(name).get(strSplit(name.trim()).size()-1);
         if(vpnExVlan.equals(A[searchIndex][2])){
              vpnData=findAllData(DATA.get(i-1).trim());
               findAllIndex= searchIndex(vpnData,"ip address");
                   if(!(findAllIndex.isEmpty())) { 
                      if(checkIpRange(midString(vpnData.get(findAllIndex.get(0)),"ip address "," "))){
                         vpnVlan=midString(name,"internal-vlanid","external-vlanid");
                         data.add(vpnVlan);
                         
                        // allMsagVlan[searchIndex].add(vpnVlan);
                        

         }
        }
       }  
      } 
     }
    }
   
   
 return data;  
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
//    public ArrayList<String> freeIpFinder(String sIp,String eIp,String sName)
  public ArrayList freeIpFinder(String range){
     
         String sIp,eIp,str;int rIndex= range.indexOf("-");       
         sIp=range.substring(0,rIndex);
         eIp=range.substring(rIndex+1,range.length());    
      
  ArrayList<Integer> di=indexOfSymbol(sIp,".");
  int a=di.get(0); int b=di.get(1); int c=di.get(2);  
   ArrayList<Integer> ei=indexOfSymbol(eIp,".");
  int d=ei.get(0); int e=ei.get(1); int f=ei.get(2);
  
String  sIpNum=""+subIp(sIp,1)+subIp(sIp,2)+subIp(sIp,3)+subIp(sIp,4); //start IP w/o .
String  eIpNum=""+subIp(eIp,1)+subIp(eIp,2)+subIp(eIp,3)+subIp(eIp,4);
 int inc=-1,sub=-1,R1=0;
 String searchStr="",searchStr1="fbsdhfbsd44t4t4###",strTokIndex="",strTokIndex1="";
 ArrayList<String> Data=new ArrayList<>();
 Data=iphostDataA;
 ArrayList<String> data=Data;
 ArrayList<String> bData=new ArrayList<>();

 
 
 // if((((subIp(sIp,3)>=0)&&(subIp(eIp,3)<64)) || (subIp(sIp,1))==196  || ((95<(subIp(sIp,3))) && ((subIp(eIp,3))<=111)))){
 if (((    subIp(sIp,3)>=0)&&(subIp(eIp,3)<64)) ||  // vbui100
        (subIp(sIp,1))==196  ||  //vbui1700
        ((95<(subIp(sIp,3))) && ((subIp(eIp,3))<=111)) || // vbui200
        (((128<=(subIp(sIp,3))) && ((subIp(eIp,3))<=255)&&((subIp(sIp,2))==85)))||
        (((96<=(subIp(sIp,3))) && ((subIp(eIp,3))<=127)&&((subIp(sIp,2))==151)))){
searchStr="ip-host description ";
searchStr1="ip host "+ subIp(sIp,1)+"."+subIp(sIp,2);
//strTokIndex1="ip host "; 
 inc=1;
 sub=32;
 R1=746;
     
 } 
 
 
//  if((63<(subIp(sIp,3)))&&((subIp(sIp,3))<96)&&(subIp(sIp,1))==10) {
  if((63<(subIp(sIp,3)))&&((subIp(eIp,3))<96)&&(subIp(sIp,1))==10&&(subIp(eIp,1))==10) {

 searchStr="ip address "+subIp(sIp,1)+"."+subIp(sIp,2);
 strTokIndex1="ip address "; 
 inc=5;
 sub=30;
 R1=748;
     
 }
 
//   if((193<(subIp(sIp,3))) && ((subIp(sIp,3))<256)) {
   if((191<(subIp(sIp,3))) && ((subIp(sIp,3))<256)&&(subIp(sIp,1))==10&&(subIp(eIp,1))==10) {
 
 searchStr="ip address "+subIp(sIp,1)+"."+subIp(sIp,2);
 strTokIndex1="ip address "; 
 inc=9;
 sub=29;
 R1=752;
       
  }
 for (int v=0;v<data.size();v++){  
      if(data.get(v).contains(searchStr)||data.get(v).contains(searchStr1)) 
       bData.add(data.get(v)+" "); 
    
    }
 

    
ArrayList<String> X=new ArrayList<>();	

for (String strIp:bData){

  //  System.out.println(strIp);
if(strIp.contains("ip-host description ")) 
    strTokIndex=midString(strIp,"ip-host description "," ")+" ";
else if(strIp.contains("ip host"))
{
    strTokIndex="ip host ";
       
}
else 
    strTokIndex=strTokIndex1;
// if(indexOfSymbol(midString(strIp,strTokIndex," "),".").size()<3){
//if(indexOfSymbol(midString(strIp,strTokIndex," "),".").size()<3){
//    kk++;
//    continue LAB;
//}
  //String h=midString(strIp,strTokIndex," ");
//System.out.println(indexOfSymbol(h,".").size()+ "==" +h) ;

 str=networkIp(midString(strIp,strTokIndex," "),sub);

     
if(((ipNum(str,3).compareTo(ipNum(sIp,3)))>=0) && ((ipNum(eIp,3).compareTo(ipNum(str,3)))>=0)){
        X.add(str); 
   
        } 
// }
}
X.removeAll(Arrays.asList("", null));
     
   ArrayList<String> ALLIP= new ArrayList<>();   
   int R,x,xx,yy;
   String x1="",mm;   
    R=subIp(eIp,3) +1-subIp(sIp,3);   
    x=subIp(sIp,3);
    xx=str2num(""+subIp(sIp,1)+subIp(sIp,2));  
    yy=(""+subIp(sIp,3)).length();
   for (int j=1;j<=R;j++)
   {
	 ArrayList<String> row= new ArrayList<>();
         for (int i=1;i<=254;i=i+inc )  {
		          if(inc>1) i=i-1;
                 			
           x1=""+xx+x+num2str(i);            // A=num2str(a); B=num2str(b); C= num2str(c+( (""+x).length() - yy));
           
           int cc=str2num(num2str(c+( (""+x).length() - yy)));         // row.add(x1);row.add(A);row.add(B); row.add(C);          
          mm= x1.substring(0,a)+"."+ x1.substring(a,b-1) +"."+x1.substring(b-1,cc-2) +"."+x1.substring(cc-2,(""+x1).length()); 
          ALLIP.add(mm);
         }      
       x=((str2num(x1.substring(b-1,x1.length()))+R1)/1000);    

   }
    
    return XOR(ALLIP,X);
     
  }
    
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
  public BigInteger ipNum ( String sIp,int index ){
  ArrayList<Integer> di=indexOfSymbol(sIp,".");
  int a=di.get(0); int b=di.get(1); int c=di.get(2);  
  BigInteger ipNum=new BigInteger("9999");
switch(index){
    case 1:
        
         ipNum=new BigInteger(""+sIp.substring(0,a)) ;
         break;
    case 2:
        
         ipNum=new BigInteger(""+sIp.substring(0,a)+sIp.substring(a+1,b));//[sIp(1:a-1) sIp(a+1:b-1)];
         break;
    case 3:        
         ipNum=new BigInteger(""+sIp.substring(0,a)+sIp.substring(a+1,b)+sIp.substring(b+1,c));//[sIp(1:a-1) sIp(a+1:b-1) sIp(b+1:c-1)]; 
         break;
    case 4:
        
         ipNum=new BigInteger(""+sIp.substring(0,a)+sIp.substring(a+1,b)+sIp.substring(b+1,c)+sIp.substring(c+1,sIp.length()));
             //[sIp(1:a-1) sIp(a+1:b-1) sIp(b+1:c-1) sIp(c+1:end)];    
          break;
}
 
 return ipNum;
}
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList XOR (ArrayList<String> src,ArrayList<String> res){
        ArrayList<String> data = new ArrayList<>();
 
   int vlan;
    for(int i = 0; i < src.size();i++){
        vlan=0;
        for (int j = 0; j <res.size(); j++) {
          if(res.get(j).equals(src.get(i))) vlan=1;  
        }
        if(vlan==0)data.add(src.get(i));
    }
       return data;
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@da
    public void copySrcDest(String src,String des){
          File sorce = new File(src);
          File dest = new File(des);     
        try {
            FileUtils.copyFile(sorce, dest);
        } catch (IOException ex) {
            Logger.getLogger(searchJFrame.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  public ArrayList ppParamFind(String input){ 
 ArrayList <String> Data=new ArrayList<>(); 
if(!(input.equals(""))){
      String Name="",Access="",Vlan,Speed="",WanIp="",exVlan="",ppInter="";
      String ff=" ",ss="";String gg="";String vrf="";String pSub ="",desc="",inter="",nameAccess="",inVlan="";
//      String[] =null;
      String[] A=input.trim().split("\n"); 
        for (int i = 0; i < A.length; i++) {
            if(A[i].contains("description"))ff=A[i].trim();
            if(A[i].contains("external-vlan"))gg=A[i].trim();           
            if((A[i].contains("interface smartgroup"))||(A[i].contains("interface gei")))inter=A[i].trim();
            if((A[i].contains("cir"))||(A[i].contains("cbs")))ss=A[i].trim();
        if(A[i].contains("255.255.255.252")&&A[i].contains("ip address")) {WanIp=hostIp(networkIp(midString(A[i],"ip address","255"),30),30,2);pSub="255.255.255.252";} 
	if(A[i].contains("255.255.255.248")&&A[i].contains("ip address")) {WanIp=hostIp(networkIp(midString(A[i],"ip address","255"),29),29,4);pSub="255.255.255.248";}
    
       }
       if(input.contains("external-vlan"))  exVlan=gg.substring(gg.trim().indexOf("external-vlan")+13,gg.length()).trim();
       if(input.contains("interface"))  ppInter=inter.substring(inter.trim().indexOf("interface")+10,inter.length()).trim();
     if(input.contains("description"))nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
  if(input.contains("cir")&&input.contains("cbs")){
  if(midString(ss,"cir","cbs").equals("66")|| midString(ss,"cir","cbs").equals("512")|| midString(ss,"cir","cbs").equals("256"))
     Speed=(midString(ss,"cir","cbs")+"");else  Speed=((""+Integer.parseInt(midString(ss,"cir","cbs"))/1024)+""); //SPEED
  }
    if(input.contains("internal-vlan")&&input.contains("external-vlan")) inVlan=midString(gg,"internal-vlan","external-vlan");     
//     Vlan=inVlan.split("[a-zA-Z]+")[inVlan.split("[a-zA-Z]+").length-1].trim();  //SVLAN   
//     WanIp=networkIp(midString("ip address","255"),30);   //WAN-IP 
    Data.add(ppInter);Data.add(inVlan);Data.add(exVlan);Data.add(WanIp);Data.add(nameAccess);Data.add(Speed);Data.add(pSub);

 }
  return Data;
 } 
    
//@@@@@@@@@@@@@@@@@@@@@@@@@@@
  public ArrayList ppParamFind(ArrayList <String> A){ 
  ArrayList <String> Data=new ArrayList<>(); 
if(A.size()!=0){    
String ff=" ",nameAccess="",inVlan="0000",exVlan="0000",pInter="",Speed="0",WanIp="0.0.0.0",pSub=""; 
      
for (int i = 0; i < A.size(); i++) {
           
	  // name      
      if(A.get(i).contains("description"))
	  nameAccess=A.get(i).trim().substring(A.get(i).trim().trim().indexOf("description")+11,A.get(i).trim().length()).trim();
	
	  //external-vlan
      if(A.get(i).contains("external-vlan"))
	  exVlan=A.get(i).trim().substring(A.get(i).trim().trim().indexOf("external-vlan")+13,A.get(i).trim().length()).trim();  
       
	   // interface	  
      if(A.get(i).contains("interface"))
	  pInter=A.get(i).trim().substring(A.get(i).trim().trim().indexOf("interface")+10,A.get(i).trim().length()).trim(); 
      
	 // name       
	   
	   // SPEED 	   
      if(A.get(i).contains("localport cir")){  
       if(midString(A.get(i),"cir","cbs").equals("66")|| midString(A.get(i),"cir","cbs").equals("512")|| midString(A.get(i),"cir","cbs").equals("256"))
       Speed=midString(A.get(i),"cir","cbs");else  Speed=""+Integer.parseInt(midString(A.get(i),"cir","cbs"))/1024;
	 
	  }
	  
	  //second-vlan
	if(A.get(i).contains("external-vlan")&& A.get(i).contains("internal-vlan")) inVlan=midString(A.get(i),"internal-vlan","external-vlan");  
    
	// wanIP
	if(A.get(i).contains("ip address")){
	
	if(A.get(i).contains("255.255.255.252")) {WanIp=hostIp(networkIp(midString(A.get(i),"ip address","255"),30),30,2); pSub="255.255.255.252";} 
	if(A.get(i).contains("255.255.255.248")) {WanIp=hostIp(networkIp(midString(A.get(i),"ip address","255"),29),29,4); pSub="255.255.255.252";} 
    
	}	
  
 }
 Data.add(pInter);Data.add(inVlan);Data.add(exVlan);Data.add(WanIp);Data.add(nameAccess);Data.add(Speed);Data.add(pSub);
 }
 return Data;
 }
//@@@@@@@@@@@@@@@@@@@@@@@@@@@  
     public ArrayList vpnParamFind(ArrayList<String> A){ 
 ArrayList <String> Data=new ArrayList<>(); 
if(A.size()!=0){  

       String ff="";String gg="";String vrf="";String selected="",desc="",inter="",vpnInter="",Vrf="",
	            nameAccess="",exVlan="0000",inVlan="0000",Name="",Access="",Vlan,Speed="0",WanIp="0.0.0.0",Vrrp="",Vvrf="";
for (int i = 0; i < A.size(); i++) {

        // name      
      if(A.get(i).contains("description"))
	  nameAccess=A.get(i).trim().substring(A.get(i).trim().trim().indexOf("description")+11,A.get(i).trim().length()).trim();
	  
	  //external-vlan
      if(A.get(i).contains("external-vlanid"))
	  exVlan=A.get(i).trim().substring(A.get(i).trim().trim().indexOf("external-vlanid")+15,A.get(i).trim().length()).trim();  
       
	   // interface	  
      if(A.get(i).contains("interface"))
	  vpnInter=A.get(i).trim().substring(A.get(i).trim().trim().indexOf("interface")+9,A.get(i).trim().length()).trim(); 
      // vrf
//      if(A.get(i).contains("ip vrf forwarding"))Vrf=A.get(i).trim().substring(A.get(i).trim().indexOf("ip vrf forwadrding")+15,A.get(i).length()).trim();
      if(A.get(i).contains("ip vrf forwarding")) Vvrf=A.get(i).trim().split("\\s+")[A.get(i).trim().split("\\s+").length-1];
//            if(A.get(i).contains("interface"))inter=A.get(i).trim();
     // SPEED 	   
//////      if(A.get(i).contains("localport cir")){  
//////       if(midString(A.get(i),"cir","cbs").equals("66")|| midString(A.get(i),"cir","cbs").equals("512")|| midString(A.get(i),"cir","cbs").equals("256"))
//////       Speed=midString(A.get(i),"cir","cbs");else  Speed=""+Integer.parseInt(midString(A.get(i),"cir","cbs"))/1024;
//////	 
//////	  }
	
if(A.get(i).contains("cir")&&A.get(i).contains("kbps cbs")){

        if(midString(A.get(i),"cir","kbps").equals("66")|| midString(A.get(i),"cir","kbps").equals("512")|| midString(A.get(i),"cir","kbps").equals("256"))
   {
       Speed=midString(A.get(i),"cir","kbps");
 
   }
   else {
       Speed=""+(Integer.parseInt(midString(A.get(i),"cir","kbps"))/1024); //SPEED
//        System.out.println(midString(ss,"cir","cbs")+" K");
    }
    }
       if(A.get(i).contains("cbs")&&A.get(i).contains("cir")&&! A.get(i).contains("kbps cbs")){

        if(midString(A.get(i),"cir","cbs").equals("66")|| midString(A.get(i),"cir","cbs").equals("512")|| midString(A.get(i),"cir","cbs").equals("256"))
   {
       Speed=midString(A.get(i),"cir","cbs");
 
   }
   else {
       Speed=""+(Integer.parseInt(midString(A.get(i),"cir","cbs"))/1024); //SPEED
//        System.out.println(midString(ss,"cir","cbs")+" K");
    }
    }


     // VRRP
       if((A.get(i).trim().contains("vrrp"))&& (A.get(i).trim().contains("accept"))) Vrrp=midString(A.get(i).trim(),"vrrp","accept");
    
	 //second-vlan
	if(A.get(i).contains("external-vlanid")&&(A.get(i).contains("internal-vlanid"))) inVlan=midString(A.get(i),"internal-vlanid","external-vlanid");  
    
	// wanIP
	if(A.get(i).contains("ip address")&&!(A.get(i).contains("secondary"))){
	
	if(A.get(i).contains("255.255.255.252")){
            WanIp=hostIp(networkIp(midString(A.get(i),"ip address","255"),30),30,2);
        } 
	if(A.get(i).contains("255.255.255.248")) 
            WanIp=hostIp(networkIp(midString(A.get(i),"ip address","255"),29),29,4); 
	 }
  
 }
  Data.add(vpnInter); Data.add(nameAccess);Data.add(Vvrf);Data.add(WanIp);
  Data.add(inVlan);Data.add(exVlan);Data.add(Speed);Data.add(Vrrp);
} 
 return Data;
}
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 //@@@@@@@@@@@@@@@@@@@@@@@@@@@   

public ArrayList vpnParamFind(String input){ 
 ArrayList <String> Data=new ArrayList<>(); 
if(!(input.equals(""))){
      String Name="",Access="",Vlan="",Speed="",WanIp="0.0.0.0",vrrp="",Vrrp="",Vvrf="",pSub="", Vrf="", vpnInter="",inVlan="";
//      String[] =null;   if(input.contains(""))
      String[] A=input.trim().split("\n"); 
      String ff="", gg="",ss="", vrf="", selected="",desc="",inter="",nameAccess="",exVlan="";
        for (int i = 0; i < A.length; i++) {
            if(A[i].contains("description"))ff=A[i].trim();
            if(A[i].contains("external-vlanid"))gg=A[i].trim();
            if(A[i].contains("ip vrf forwarding"))vrf=A[i].trim();
            if((A[i].contains("interface"))&&!(A[i].contains("route-map Toger")))inter=A[i].trim();
            if((A[i].contains("cir"))&&(A[i].contains("cbs")))ss=A[i].trim();
            if((A[i].contains("vrrp"))&&(A[i].contains("accept")))vrrp=A[i].trim();
              if(A[i].contains("255.255.255.252")&&!(A[i].contains("secondary"))) 
              {WanIp=hostIp(networkIp(midString(A[i],"ip address","255"),30),30,2);
              pSub="255.255.255.252";} 
  	      if(A[i].contains("255.255.255.248")&&!(A[i].contains("secondary"))) 
              {WanIp=hostIp(networkIp(midString(A[i],"ip address","255"),29),29,4);
              pSub="255.255.255.248";}
        }
       
        if(input.contains("external-vlanid"))exVlan=gg.substring(gg.trim().indexOf("external-vlanid")+15,gg.length()).trim();
        if(input.contains("interface"))vpnInter=inter.substring(inter.trim().indexOf("interface")+10,inter.length()).trim();
        if(input.contains("ip vrf forwarding")) Vrf=vrf.substring(vrf.trim().indexOf("ip vrf forwarding")+17,vrf.length()).trim();
    if(input.contains("description"))nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
    if(ss.contains("cir")&&ss.contains("kbps cbs")){

        if(midString(ss,"cir","kbps").equals("66")|| midString(ss,"cir","kbps").equals("512")|| midString(ss,"cir","kbps").equals("256"))
   {
       Speed=midString(ss,"cir","kbps");
 
   }
   else {
       Speed=""+(Integer.parseInt(midString(ss,"cir","kbps"))/1024); //SPEED
//        System.out.println(midString(ss,"cir","cbs")+" K");
    }
    }
       if(ss.contains("cbs")&&ss.contains("cir")&&! ss.contains("kbps cbs")){

        if(midString(ss,"cir","cbs").equals("66")|| midString(ss,"cir","cbs").equals("512")|| midString(ss,"cir","cbs").equals("256"))
   {
       Speed=midString(ss,"cir","cbs");
 
   }
   else {
       Speed=""+(Integer.parseInt(midString(ss,"cir","cbs"))/1024); //SPEED
//        System.out.println(midString(ss,"cir","cbs")+" K");
    }
    }
     if(input.contains("internal-vlanid")&&input.contains("external-vlanid")) inVlan=midString(gg,"internal-vlanid","external-vlanid");     
//     Vvrf=Vrf.split("\\s+")[Vrf.split("\\s+").length-1];   //VRF
    
  if(input.contains("vrrp")&&input.contains("accept")) Vrrp=midString(vrrp,"vrrp","accept"); else Vrrp=""; // VRRP
     activity="Line Shift";

     Data.add(vpnInter); Data.add(nameAccess);Data.add(Vrf);Data.add(WanIp);
     Data.add(inVlan);Data.add(exVlan);Data.add(Speed);Data.add(Vrrp);Data.add(pSub);
 }
  return Data;
 } 
    
    
    
 public ArrayList newLiner (String input,String tobeFind,String tobeEliminated){

    String b="",B,qinqInter=""; boolean c;
    ArrayList<Integer> qinqIndex=new ArrayList<>();
    ArrayList<String> in=new ArrayList <>();
    ArrayList<String> allQinq=new ArrayList <>();
if((!input.trim().equals(""))&&(input.contains(tobeFind))){
    for(String AA:input.split("\n"))in.add(AA);
    
    qinqIndex=searchIndex(in,tobeFind);

    for(int p=((qinqIndex.size())-1);p>=0;p--)
           {
              B=in.get((qinqIndex.get(p))+1);
               ;
               if (B.contains(tobeFind)) {
                   b="";
                   allQinq.add(in.get(qinqIndex.get(p))+b);
                 
                   }
               if(!(B.contains(tobeFind))){
                   if(!(in.get((qinqIndex.get(p))+1).contains(tobeEliminated)))
                   b=in.get((qinqIndex.get(p))+1);
                   else b="";
                   
                   allQinq.add(in.get(qinqIndex.get(p))+b);
                  
                 
                  }               
              
               }
}       
return allQinq;

}
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    public ArrayList vpnParamFind(){ 
        ArrayList <String> Data=new ArrayList<>(); 
if(!(outputtext.getText().equals(""))){
      String Name="",Access="",Vlan,Speed,WanIp="0.0.0.0",Vrrp="",Vvrf,pSub="";   
      String[] A=outputtext.getText().trim().split("\n"); String ff="";String gg="";String vrf="";String selected="",desc="",inter="",nameAccess="";
        for (int i = 0; i < A.length; i++) {
            if(A[i].contains("description"))ff=A[i].trim();
            if(A[i].contains("external-vlanid"))gg=A[i].trim();
            if(A[i].contains("ip vrf forwarding"))vrf=A[i].trim();
            if(A[i].contains("interface"))inter=A[i].trim();
              if(A[i].contains("255.255.255.252")&&!(A[i].contains("secondary"))) {WanIp=hostIp(networkIp(midString(A[i],"ip address","255"),30),30,0);pSub="255.255.255.252";} 
  	      if(A[i].contains("255.255.255.248")&&!(A[i].contains("secondary"))) {WanIp=hostIp(networkIp(midString(A[i],"ip address","255"),29),29,0);pSub="255.255.255.248";}
  
        }
        String exVlan=gg.substring(gg.trim().indexOf("external-vlanid")+15,gg.length()).trim();
        String vpnInter=inter.substring(gg.trim().indexOf("interface")+10,inter.length()).trim();
        String Vrf=vrf.substring(vrf.trim().indexOf("ip vrf forwadrding")+17,vrf.length()).trim();
//       vlan.setText(exVlan.split("\\s+")[exVlan.split("\\s+").length-1]);
     selected=outputtext.getSelectedText();    
     selected=outputtext.getSelectedText();    
   
       nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
     if(!(selected==null)){
        Name=nameAccess.replace(selected,"").replace("_"," ").replace("-"," ");   //NAME    
        Access=selected;                       //ACCESS
     }else if((selected==null)){
             Access="";      //ACCESS
             Name=nameAccess; //NAME
        }  
     
     if(!(Name.equals(""))&& !(Access.equals("")))desc=(Access+"_"+Name).trim();
     else if(Access.equals("")) desc=nameAccess;
   
     if(midString("cir","kbps").equals("66")|| midString("cir","kbps").equals("512")|| midString("cir","kbps").equals("256"))
     Speed=midString("cir","kbps");else  Speed=""+Integer.parseInt(midString("cir","kbps"))/1024; //SPEED
     String inVlan=midString("internal-vlanid","external-vlanid");     
     Vlan=inVlan.split("[a-zA-Z]+")[inVlan.split("[a-zA-Z]+").length-1].trim();  //SVLAN   
//     WanIp=networkIp(midString("ip address","255"),29);   //WAN-IP
     
     Vvrf=Vrf.split("\\s+")[Vrf.split("\\s+").length-1];   //VRF
    if(outputtext.getText().contains("vrrp")) Vrrp=midString("vrrp","accept"); else Vrrp=""; // VRRP
     activity="Line Shift";
//     searchIndex=-1;
      Data.add(Name);Data.add(Access);Data.add(Speed);Data.add(Vlan);Data.add(WanIp);Data.add(Vvrf);Data.add(Vrrp);Data.add(vpnInter);
      msag.setText("");msagIp.setText("");vlan.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
//   outputtext.setText("");   
//   outputtext.append(vpnConfig(desc,Speed,Vlan,WanIp," "," ",Vrf,Vrrp,vpnInter));
                 
   }
  return Data;
 } 
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
      public ArrayList<String> bbParamFind(String input){
      
        ArrayList<String> data=new ArrayList<>();  
        
 if(input.contains("ip-host")||input.contains("author-temp-name")){
     
      if(!(input.equals(""))){
      String speed="",svlan="",wanIp="",Interface="",cvlan="",slot="",port="",descr="";
      String[] A=input.trim().split("\n"); String ff=""; 
      for (int k = 0; k < A.length; k++){
          
      for (int i = 0; i < A.length; i++) {
           // if(A[i].contains("description")&&A[i].contains("ip-host"))ff=A[i].trim(); 
            if(A[i].contains("smartgroup")&&A[i].contains("ip-host"))ff=A[i].trim();
          }  
      String [] config=ff.split("\\s+");
        
      if(ff.contains("description "))
       //descr=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
      descr=config[2];
      else descr=" ";
      if(input.contains("sec-vlan")){

            if((input.contains("vlan")&&input.contains("sec-vlan")))cvlan=midString(input,"vlan","sec-vlan");
            if((input.contains("sec-vlan")&&input.contains("author-temp-name")))svlan=midString(input,"sec-vlan","author-temp-name");
        }
        else {

           if((input.contains("vlan")&&input.contains("author-temp-name"))) {
////         if(!(input.contains("up-rate"))) cvlan=midString(input,"vlan","down-rate");
////         else 
             cvlan=midString(input,"vlan","author-temp-name");           
             svlan="";
           }
        }
//if(input.contains("author-temp-name")) speed=midString(input,"up-rate","down-rate"); else speed="0";     
   if(input.contains("author-temp-name")) 
       //speed=midString(input,"up-rate","down-rate"); 
        speed=ff.substring(ff.trim().indexOf("author-temp-name")+16,ff.length()).trim();
    else speed="0";      
        if(input.contains("slot")&&input.contains("port")){
           wanIp=midString(input,"ip host","slot");
           slot=midString(input,"slot","port");
           port=midString(input,"port","vlan");
           Interface=" slot "+slot+" port "+port;        
           data.add(wanIp);data.add(Interface);data.add(slot);data.add(port);data.add(cvlan);data.add(svlan);data.add(speed);data.add(descr);;
        }
         else if((input.contains("ip-host"))&&(input.contains("smartgroup"))){
          //ip-host description DFGAF_NAME26Mar19 10.150.48.76 smartgroup3.1010 vlan 2641 sec-vlan 1234  author-temp-name YK-1M
           // wanIp=midString(input,"ip-host","smartgroup");
            wanIp=config[searchIndex(config,"smartgroup").get(0)-1];
            Interface=config[searchIndex(config,"smartgroup").get(0)];//Interface=midString(input,wanIp,"vlan");
            data.add(wanIp);data.add(Interface);data.add(cvlan);data.add(svlan);data.add(speed);data.add(descr);;
        } 
     }
        }
      }
      if(input.contains("ip host")){
          
             if(!(input.equals(""))){
      String speed="",svlan="",wanIp="",Interface="",cvlan="",slot="",port="",descr="";
      String[] A=input.trim().split("\n"); String ff=""; 
      for (int k = 0; k < A.length; k++){
          
      
      for (int i = 0; i < A.length; i++) {
            if(A[i].contains("description"))ff=A[i].trim(); 
      }   
        
if(ff.contains("description "))descr=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();else descr=" ";
     if(input.contains("second-vlan")){
//            vlan.setText(midString("vlan","second-vlan"));
            if((input.contains("vlan")&&input.contains("second-vlan")))cvlan=midString(input,"vlan","second-vlan");
            if((input.contains("second-vlan")&&input.contains("up-rate")))svlan=midString(input,"second-vlan","up-rate");
        }else {
//            vlan.setText(midString("vlan","up-rate"));
           if((input.contains("vlan")&&input.contains("up-rate"))||(input.contains("vlan")&&input.contains("down-rate"))) {
               if(!(input.contains("up-rate"))) cvlan=midString(input,"vlan","down-rate");
               else 
                   cvlan=midString(input,"vlan","up-rate");           
               svlan="";
           }
        }
if(input.contains("up-rate")&&input.contains("down-rate")) speed=midString(input,"up-rate","down-rate"); else speed="0";     
        
        if(input.contains("slot")&&input.contains("port")){
           wanIp=midString(input,"ip host","slot");
           slot=midString(input,"slot","port");
           port=midString(input,"port","vlan");
           Interface=" slot "+slot+" port "+port;        
           data.add(wanIp);data.add(Interface);data.add(slot);data.add(port);data.add(cvlan);data.add(svlan);data.add(speed);data.add(descr);;
        }
         else if(!(input.contains("slot")&&input.contains("port"))&&(input.contains("smartgroup"))){
            wanIp=midString(input,"ip host","smartgroup");
            Interface=midString(input,wanIp,"vlan");
            data.add(wanIp);data.add(Interface);data.add(cvlan);data.add(svlan);data.add(speed);data.add(descr);;
        } 
     }
        }
      }
//        for(String g:data)System.out.println(g);
        return data;
        
    }
    
    
    
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList bbParamFind(){
      ArrayList data=new ArrayList<>(); 
        if(!(outputtext.getText().equals(""))){
      String speed="",svlan="",wanIp="",Interface="",cvlan="",slot="",port="",descr="";
      String[] A=outputtext.getText().trim().split("\n"); String ff="";    
      for (int i = 0; i < A.length; i++) {
            if(A[i].contains("description"))ff=A[i].trim();  }     
        
      descr=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
     if(outputtext.getText().contains("second-vlan")){
//            vlan.setText(midString("vlan","second-vlan"));
            cvlan=midString("vlan","second-vlan");
            svlan=midString("second-vlan","up-rate");
        }else {
//            vlan.setText(midString("vlan","up-rate"));
            cvlan=midString("vlan","up-rate");
            svlan="";
        }
        speed=midString("up-rate","down-rate");      
       if(outputtext.getText().contains("slot")&&outputtext.getText().contains("port")){
           wanIp=midString("ip host","slot");
           slot=midString("slot","port");
           port=midString("port","vlan");
           Interface=" slot "+slot+" port "+port;        
           data.add(wanIp);data.add(Interface);data.add(slot);data.add(port);data.add(cvlan);data.add(svlan);data.add(speed);data.add(descr);;
        }
         else if(!(outputtext.getText().contains("slot")&&outputtext.getText().contains("port"))){
            wanIp=midString("ip-host","smartgroup");
            Interface=midString(wanIp,"vlan");
            data.add(wanIp);data.add(Interface);data.add(cvlan);data.add(svlan);data.add(speed);data.add(descr);;
        } 
      
        searchIndex=-1;msag.setText("");msagIp.setText("");vlan.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
  }
        return data;
    }
    
    
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
    
  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 public void xmlArrayWrite(ArrayList<ArrayList<String>> twoD,String Name) {     
 String name=Name+".xml";       
try{
    FileOutputStream data=new FileOutputStream(new File(name));
    XMLEncoder encoder=new XMLEncoder (data);
    encoder.writeObject(twoD);
    encoder.close();
    data.close();
 }catch(Exception e){}   
 } 
  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 
 
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 public void xmlListWrite(ArrayList twoD,String Name) {     
String name=Name+".xml";          
try{
    FileOutputStream data=new FileOutputStream(new File(name));
    XMLEncoder encoder=new XMLEncoder (data);
    encoder.writeObject(twoD);
    encoder.close();
    data.close();
 }catch(Exception e){}   
 } 
  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 
  
  
public void xmlStringWrite(String oneD,String Name) {     
 String name=Name+".xml";         
try{
    FileOutputStream data=new FileOutputStream(new File(name));
    XMLEncoder encoder=new XMLEncoder (data);
    encoder.writeObject((Object)oneD);
    encoder.close();
    data.close();
 }catch(Exception e){}   
 }        
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  
public String xmlStringRead(String Name){ 
    String name=Name+".xml";  
     String DATA="";
    try{
    FileInputStream data=new FileInputStream(new File(name));
    XMLDecoder decoder=new XMLDecoder (data);
    DATA =(String)decoder.readObject();
    decoder.close();
     data.close();
     }catch(Exception e){} 
     return DATA;   
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  
public ArrayList<String> xmlListRead(String Name){ 
    String name=Name+".xml";  
     ArrayList <String> DATA= new ArrayList<>();
    try{
    FileInputStream data=new FileInputStream(new File(name));
    XMLDecoder decoder=new XMLDecoder (data);
    DATA =(ArrayList)decoder.readObject();
    decoder.close();
     data.close();
     }catch(Exception e){} 
     return DATA;   
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  
public ArrayList<ArrayList<String>> xmlArrayRead(String Name){ 
   String name=Name+".xml";   
      ArrayList<ArrayList<String>> DATA= new ArrayList<ArrayList<String>>();
    try{
    FileInputStream data=new FileInputStream(new File(name));
    XMLDecoder decoder=new XMLDecoder (data);
    DATA =(ArrayList)decoder.readObject();
    decoder.close();
    data.close();
     }catch(Exception e){} 
     return DATA;   
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public void xlWrite(String Name,String ExcelName){
     
ArrayList<ArrayList<String>> DATA=xmlArrayRead(Name);
        
 String [][] NewData=new String[DATA.size()][DATA.get(0).size()];

 for (int i = 0; i < DATA.size(); i++) {
    ArrayList<String> row = DATA.get(i);
    NewData[i] = row.toArray(new String [row.size()]);
}
 
  String outExcelFilePath = ExcelName+".xls";

  String excelFilePath = "reset/ipAssigned.xls";

                                
        FileInputStream file = null;        
        try {
            file = new FileInputStream(new File(excelFilePath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(searchJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
         HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(file);
        } catch (IOException ex) {
            Logger.getLogger(searchJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
             HSSFFont font = wb.createFont();
             CellStyle style = wb.createCellStyle();
             font.setColor(HSSFColor.BLACK.index); 
             style.setFont(font);
             style.setBorderLeft(BorderStyle.THIN);
             style.setBorderTop(BorderStyle.THIN);
             style.setBorderBottom(BorderStyle.THIN);
             style.setBorderRight(BorderStyle.THIN);
             style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
             style.setFillPattern(CellStyle.SOLID_FOREGROUND);

         Sheet sheet = wb.getSheetAt(0);
         int rownum = 0;
         for (Object [] data:NewData) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (Object obj : data) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String){
                    cell.setCellValue((String) obj);
                    
                    cell.setCellStyle(style);}                  
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(outExcelFilePath));
//            workbook.write(out);
            wb.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     public String midString(String SS,String pat1,String pat2){

         String text=SS;        
        Pattern p=Pattern.compile(Pattern.quote(pat1)+"(.*?)"+Pattern.quote(pat2));
        Matcher m=p.matcher(text);      
        ArrayList<String> extract=new ArrayList<>();
        while(m.find()) extract.add(m.group(1));       
        return extract.get(0).trim();
       }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     public String midString(String pat1,String pat2){
        String text=outputtext.getText();        
        Pattern p=Pattern.compile(Pattern.quote(pat1)+"(.*?)"+Pattern.quote(pat2));
        Matcher m=p.matcher(text);
        ArrayList<String> extract=new ArrayList<>();
        while(m.find()) extract.add(m.group(1));       
        return extract.get(0).trim();
       }
     //@@@@@@@@@@@@@@@@@@@@@@@@@@@
     public String getIpAllocFromInterface(String inter,int index){
         ArrayList<String> iphostData1=iphostDataA;      
        String hostName="",hn="";       
            for (int i =index ; i >=0; i--) {
                  if(iphostData1.get(i).contains("hostname ") && iphostData1.get(i).contains("-ER-DATA-")){
                      hostName=iphostData1.get(i);
                      hn=midString(hostName,"hostname","-ER-DATA-"); 
                      break;
                     }
                  if(iphostData1.get(i).contains("hostname ") && iphostData1.get(i).contains("-PE-")){
                      hostName=iphostData1.get(i);
                      hn=midString(hostName,"hostname","-PE-").split("[0-9]+")[1].replace("-", ""); 
                      break;
                  }
                   
                  
                   }
            
//       return midString(hostName,"hostname","-ER-DATA-");   
         return hn; 
     }
          //@@@@@@@@@@@@@@@@@@@@@@@@
     public String getSpeedPrefix(String wan){      
        String range="",sIP,eIP;
        int rIndex=-1,X=0,Y=0,noHost,sub;       
//        int ipRangeIndex=findIndex(dataColls(IPrange,0),A[searchIndex][17]); 
     
  for (int j = 1; j < IPrange.length; j++) {
            
     for (int i = 1; i < IPrange[0].length-1; i++) {       
         
          range=IPrange[j][i];
          rIndex= range.indexOf("-");
     
          sIP=range.substring(0,rIndex); 
          eIP=range.substring(rIndex+1,range.length());      
          noHost=(subIp(eIP,1)-subIp(sIP,1)+1)*(subIp(eIP,2)-subIp(sIP,2)+1)*(subIp(eIP,3)-subIp(sIP,3)+1)*(subIp(eIP,4)-subIp(sIP,4)+3);
          sub=1+(Integer.numberOfLeadingZeros(noHost));
          if(networkIp(sIP,sub).compareTo(networkIp(wan,sub))==0){ X=j;}
         }
        }
        
        return IPrange[X][IPrange[0].length-1];
        
    }
     //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     //@@@@@@@@@@@@@@@@@@@@@@@@
     public String getIpAlloc(String wan){      
        String range="",sIP,eIP;
        int rIndex=-1,X=0,Y=0,noHost,sub;       
//        int ipRangeIndex=findIndex(dataColls(IPrange,0),A[searchIndex][17]); 
     
  for (int j = 1; j < IPrange.length; j++) {
            
     for (int i = 1; i < IPrange[0].length-1; i++) {       
         
          range=IPrange[j][i];
          rIndex= range.indexOf("-");
          sIP=range.substring(0,rIndex); 
          eIP=range.substring(rIndex+1,range.length());      
          noHost=(subIp(eIP,1)-subIp(sIP,1)+1)*(subIp(eIP,2)-subIp(sIP,2)+1)*(subIp(eIP,3)-subIp(sIP,3)+1)*(subIp(eIP,4)-subIp(sIP,4)+3);
          sub=1+(Integer.numberOfLeadingZeros(noHost));
          if(networkIp(sIP,sub).compareTo(networkIp(wan,sub))==0){ X=j;}
         }
        }
         
        return IPrange[X][0];
    }
     //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
          //@@@@@@@@@@@@@@@@@@@@@@@@
     public String[] getIpRange(String wan){      
        String range="",sIP,eIP;
        String[] result=new String[2];
        int rIndex=-1,X=0,Z=0,Y=0,noHost,sub;       
//        int ipRangeIndex=findIndex(dataColls(IPrange,0),A[searchIndex][17]); 
     
  for (int j = 1; j < IPrange.length; j++) {
            
     for (int i = 1; i < IPrange[0].length-1; i++) {       
         
          range=IPrange[j][i];
          rIndex= range.indexOf("-");
          sIP=range.substring(0,rIndex); 
          eIP=range.substring(rIndex+1,range.length());      
          noHost=(subIp(eIP,1)-subIp(sIP,1)+1)*(subIp(eIP,2)-subIp(sIP,2)+1)*(subIp(eIP,3)-subIp(sIP,3)+1)*(subIp(eIP,4)-subIp(sIP,4)+3);
          sub=1+(Integer.numberOfLeadingZeros(noHost));
          if(networkIp(sIP,sub).compareTo(networkIp(wan,sub))==0){ 
            Z=i;
            result=IPrange[0][Z].trim().split("\\s+");
            if(result[1].contains("broadband")) result[0]="interface "+result[0];
          }
         }
        }
         
        return result;
                
    }
     //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  public boolean isInRange(String Input,String sVlan){      
        String range="";
        int sIP,eIP;
        boolean c=false;
        String []s=Input.substring(Input.indexOf("second-dot1q ")+13,Input.length()).trim().split("[\\s,]");
        int rIndex=-1;           
  for (int j = 0; j < s.length; j++) {
          if(!(s[j].contains("-"))){
              if(str2num(s[j])==str2num(sVlan)){
                  c=true;break;
              }              
          }  
          if(s[j].contains("-")){
          range=s[j];
          rIndex= range.indexOf("-");
          sIP=str2num(range.substring(0,rIndex)); 
          eIP=str2num(range.substring(rIndex+1,range.length()));   
          if((sIP<=str2num(sVlan))&&(str2num(sVlan)<=eIP)){
              c=true;break;
          }
          }
         
       }
        
         
        return c;
    }
     //@@@@@@@@@@@@@@@@@@@@@@@@@
 public String qinqFinder (String VLAN,String sVlan,String ipAlloc){

    String b="",B,allQinq="",qinqInter=""; boolean c;
    ArrayList<Integer> qinqIndex=new ArrayList<>();
    ArrayList<String> iphostData1=iphostDataA;
    qinqIndex=searchIndex(iphostData1,"qinq "+VLAN);

    for(int p=((qinqIndex.size())-1);p>=0;p--)
           {
               int ipRangeIndex=findIndex(dataColls(A,17),ipAlloc); 

               if((findBrasHostName(qinqIndex.get(p)).equals("ip address "+String.valueOf(A[ipRangeIndex][7])+" 255.255.255.252"))||
                       (findBrasHostName(qinqIndex.get(p)).equals("ip address "+String.valueOf(A[ipRangeIndex][8])+" 255.255.255.252"))){
                
                   B=iphostData1.get((qinqIndex.get(p))+1);
               ;
               if (B.contains("qinq")) {
                   b="";
                   allQinq=(iphostData1.get(qinqIndex.get(p))+b);
                 
                   }
               if(!(B.contains("qinq"))){
                   if(!(iphostData1.get((qinqIndex.get(p))+1).contains("out_index")))
                   b=iphostData1.get((qinqIndex.get(p))+1);else b="";
                   
                   allQinq=(iphostData1.get(qinqIndex.get(p))+b);
                  
                 
                  }
               
               if(!allQinq.equals(""))
                   if(isInRange(allQinq,sVlan)) {
                       qinqInter=findQinqConflict(qinqIndex.get(p));
                       
                   }
               }

           }
return qinqInter;
}
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      public String findErHostName(int index){
        ArrayList<String> iphostData1=iphostDataA;      
        String hostName="";       
            for (int i =index ; i >=0; i--) {
                  if(iphostData1.get(i).contains("hostname ") && (iphostData1.get(i).contains("-ER-DATA-")||iphostData1.get(i).contains("-PE-"))){
                      hostName=iphostData1.get(i);break;}
                   }
    
       return hostName;
       } 
      
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        public String findQinqConflict(int index){
        ArrayList<String> iphostData1=iphostDataA;      
        String hostName="";       
            for (int i =index ; i >=0; i--) {
                  if(iphostData1.get(i).contains("interface")&&iphostData1.get(i).contains("bras")){
                      hostName=iphostData1.get(i);break;}
                   }
    
       return hostName;
       }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
      public String findBrasHostName(int index){
        ArrayList<String> iphostData1=iphostDataA;      
        String hostName="";       
            for (int i =index ; i >=0; i--) {
                  if(iphostData1.get(i).trim().equals("encapsulation dot1Q 10")){
                      hostName=iphostData1.get(i+1);break;}
                   }
    
       return hostName.trim();
       }  
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public String noMsag(){
        return ("\n\n       @Tamiru M$lla                           "
                + "                                                                                "
                + "                                                                                 "
                + "                                         WT-Solution\n\n"
                +  "                                                                                  "
                + "   $$$$$$$$$$$$$$$$         Select MSAG !!!!!   $$$$$$$$$$$$$$$$");
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public int msagFinder(String vlan,String inter,String ipAlloc,String type){
         String msag="";int index,mIndex=-1;
         ArrayList<Integer> sInd= new ArrayList<>();
         switch(type){
             case "BRAS":
               sInd=searchIndex(dataColls(A,2),vlan,""); 
                 for (int i = 0; i < sInd.size(); i++) {
                   index=sInd.get(i); 
                   if((String.valueOf(A[index][4]).trim().equals(inter))&&
                       (String.valueOf(A[index][17]).trim().equals(ipAlloc)))
                    mIndex=sInd.get(i);                          
                   
                 }
                 if(mIndex>=0)msag=String.valueOf(A[mIndex][0]); 
                 else msag="UN-KNOWN";
                 
              break;
             case "ER":
                sInd=searchIndex(dataColls(A,2),vlan,""); 
                 for (int i = 0; i < sInd.size(); i++) {
                   index=sInd.get(i); 
                   if((String.valueOf(A[index][6]).trim().equals(inter))&&
                       (String.valueOf(A[index][17]).trim().equals(ipAlloc)))
                    mIndex=sInd.get(i);  
                     
                 
                 }
                 if(mIndex>=0)msag=String.valueOf(A[mIndex][0]); 
                 else msag="UN-KNOWN";
                 
                     
              break;
                 
         }
       return mIndex;  
     }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public void fillMsag(int mIndex){
  if(mIndex!=-1) { 
      int index=mIndex;
         searchIndex=index;         
         String temp =String.valueOf(A[index][10]);         
         String temp2="MSAN";
         String temp3="MDU";
         
 if (temp.contains(temp2)|| temp.contains(temp3)) {
 msagIp.setText("telnet "+String.valueOf(A[index][1])+" vrf OAM"); 
 }  else{  
 msagIp.setText("telnet "+String.valueOf(A[index][1])+" 1123 vrf OAM"); 
 }
 vlanTag.setText(String.valueOf(A[index][2]));
 msagTag.setText(String.valueOf(A[index][0]));msagTag.setHorizontalAlignment(JLabel.CENTER);
 area.setText(String.valueOf(A[index][3]));area.setHorizontalAlignment(JLabel.CENTER);
  } else{
 
 msagIp.setText(""); msagIp.setHorizontalAlignment(JLabel.CENTER);  
 vlanTag.setText("");
 msagTag.setText("");msagTag.setHorizontalAlignment(JLabel.CENTER);
 area.setText("");area.setHorizontalAlignment(JLabel.CENTER); 
  }
  
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     public void bbConfig (String description,String SSpeed,String VlaN,String WanIP){
     if(A[searchIndex][6].contains("smartgroup")){
         
      outputtext.setText("");
      String smartG=""; String sg="";String vbui="";String qinq="";String WanIp="0.0.0.0";String Vlan="0000";String Speed="";
       String ipGrp=A[searchIndex][17];int index=findIndex(dataColls(IPrange,0),ipGrp); String ipStart=IPrange[index][1].substring(0,7)+"0.1";    
       if(WanIP.equals(""))WanIp=ipStart;else WanIp=WanIP;
//       if(!WanIP.equals("")) WanIp=WanIP;
       if(!VlaN.equals(""))Vlan=VlaN;
        String sp=A[searchIndex][4];int iOf=sp.indexOf("/");
          int temp=subIp(WanIp,3);
          int temp2=subIp(WanIp,2);
       if(A[searchIndex][4].contains("/")){
            smartG=("slot "+sp.substring(0,iOf)+" port "+sp.substring(iOf+1,sp.length()));
          
        if(temp>=0&&temp<=63&&temp2!=85&&temp2!=151){
             vbui="interface vbui100";
             qinq=qinqvbui100(searchIndex);
             ipRangeIndexer=findIndex(IPrange[0],"vbui100 broadband");
        }
         if(temp>=128&&temp<=255&&temp2==85){
             vbui="interface vbui101";
             qinq=qinqvbui101(searchIndex);
             ipRangeIndexer=findIndex(IPrange[0],"vbui101 broadband");
        }
        
         if(temp>=96&&temp<=111&&temp2!=85&&temp2!=151){
             vbui="interface vbui200";
             qinq=qinqvbui200(searchIndex);
             ipRangeIndexer=findIndex(IPrange[0],"vbui200 broadband");
        }
        if(temp>=96&&temp<=127&&temp2==151){
             vbui="interface vbui300";
             qinq=qinqvbui300(searchIndex);
             ipRangeIndexer=findIndex(IPrange[0],"vbui300 broadband");
        }
        if((WanIp.trim().substring(0,3).equals("196"))){
            vbui="interface vbui1700";
            qinq=qinqvbui1700(searchIndex);
            ipRangeIndexer=findIndex(IPrange[0],"vbui1700 broadband");
        }
       }else{
         if(temp>=0&&temp<=63&&temp2!=85){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][5]);
            vbui="interface vbui100";
            qinq=qinqvbui100(searchIndex);
            Speed=speedN(SSpeed,WanIp);
        }
          if(temp>=128&&temp<=255&&temp2==85){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][21]);
            vbui="interface vbui101";
            qinq=qinqvbui101(searchIndex);
            Speed=speedN(SSpeed,WanIp);
        }
         if(temp>=96&&temp<=111&&temp2!=85&&temp2!=151){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][16]);
            vbui="interface vbui200";
            qinq=qinqvbui200(searchIndex);
            Speed=speedN(SSpeed,WanIp);
        } 
          if(temp>=96&&temp<=127&&temp2==151){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][22]);
            vbui="interface vbui300";
            qinq=qinqvbui300(searchIndex);
            Speed=speedN(SSpeed,WanIp);
        }
         
        if((WanIp.trim().substring(0,3).equals("196"))){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][9]);
            vbui="interface vbui1700";
            qinq=qinqvbui1700(searchIndex);
            Speed=speedN(SSpeed,WanIp);
            
        }
        }
       

         //String config = "ip host WANIP SMARTG vlan VLAN second-vlan INNER "
              //   + " up-rate SPEED down-rate SPEED description ACESSE_NAME";
         
         String config = "ip-host description ACESSE_NAME WANIP SMARTG vlan VLAN sec-vlan INNER "
                 + " author-temp-name SPEED ";
         
//         String bName=Name.trim().toUpperCase().replaceAll("\\s++","_");
         String[]exp={"WANIP" ,"SMARTG" ,"VLAN" ,"INNER" ,"SPEED" ,"ACESSE_NAME"};
         String[]rep={WanIp.trim(),smartG,A[searchIndex][2],Vlan.trim(),Speed,description}; 
		 for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]);
                 
            String[] D=config.split("\\s+");
            String d=D[(D.length)-1].trim();
           int comp=d.length();
           if(comp>31){
              findRemark.setText("Description exceed the limit by: "+(comp-31));findRemark.setHorizontalAlignment(JLabel.CENTER); 
           }else{
              findRemark.setText("Description exceed the limit by: 0");findRemark.setHorizontalAlignment(JLabel.CENTER);             
           }    
                 
         outputtext.append("\n\n     config t");
         outputtext.append("\n     vbui-configuration");
         outputtext.append("\n     "+vbui);                
         outputtext.append("\n  "+config);
         outputtext.append("\n"+"  $");
         outputtext.append("\n"+"  $");
         outputtext.append("\n\n   vlan-configuration");
         outputtext.append("\n    "+qinq);
         outputtext.append("\n         qinq range internal-vlan-range "+Vlan.trim()+" external-vlan-range "+A[searchIndex][2]);
         outputtext.append("\n\n");
         updateTask(config);
         //dataUpdater
        
     }
     if(!A[searchIndex][6].contains("smartgroup")){
                  outputtext.setText("");
      String smartG=""; String sg="";String vbui="";String qinq="";String WanIp="0.0.0.0";String Vlan="0000";
       String ipGrp=A[searchIndex][17];int index=findIndex(dataColls(IPrange,0),ipGrp); String ipStart=IPrange[index][1].substring(0,7)+"0.1";    
       if(WanIP.equals(""))WanIp=ipStart;else WanIp=WanIP;
//       if(!WanIP.equals("")) WanIp=WanIP;
       if(!VlaN.equals(""))Vlan=VlaN;
        String sp=A[searchIndex][4];int iOf=sp.indexOf("/");
          int temp=subIp(WanIp,3);
          int temp2=subIp(WanIp,2);
       if(A[searchIndex][4].contains("/")){
            smartG=("slot "+sp.substring(0,iOf)+" port "+sp.substring(iOf+1,sp.length()));
          
        if(temp>=0&&temp<=63&&temp2!=85){
             vbui="interface vbui100";
             qinq=qinqvbui100(searchIndex);
        }
         if(temp>=128&&temp<=255&&temp2==85){
             vbui="interface vbui101";
             qinq=qinqvbui101(searchIndex);
        }
         if(temp>=96&&temp<=111&&temp2!=151&&temp2!=85){
             vbui="interface vbui200";
             qinq=qinqvbui200(searchIndex);
        }
          if(temp>=96&&temp<=127&&temp2==151){
             vbui="interface vbui300";
             qinq=qinqvbui300(searchIndex);
        }
        if((WanIp.trim().substring(0,3).equals("196"))){
            vbui="interface vbui1700";
            qinq=qinqvbui1700(searchIndex);
            
        }
       }else{
         if(temp>=0&&temp<=63&&temp2!=85){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][5]);
            vbui="interface vbui100";
            qinq=qinqvbui100(searchIndex);
        }
          if(temp>=0&&temp<=63&&temp2!=85){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][21]);
            vbui="interface vbui101";
            qinq=qinqvbui101(searchIndex);
        }
         if(temp>=96&&temp<=111&&temp2!=85&&temp2!=151){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][16]);
            vbui="interface vbui200";
            qinq=qinqvbui200(searchIndex);
        } 
           if(temp>=96&&temp<=127&&temp2==151){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][22]);
            vbui="interface vbui300";
            qinq=qinqvbui300(searchIndex);
        } 
         
        if((WanIp.trim().substring(0,3).equals("196"))){
            smartG=("smartgroup"+A[searchIndex][4]+"."+A[searchIndex][9]);
            vbui="interface vbui1700";
            qinq=qinqvbui1700(searchIndex);
            
        }
        }
       String Speed=Integer.toString(speed(SSpeed));

         String config = "ip host WANIP SMARTG vlan VLAN second-vlan INNER "
                 + " up-rate SPEED down-rate SPEED description ACESSE_NAME";
//         String bName=Name.trim().toUpperCase().replaceAll("\\s++","_");
         String[]exp={"WANIP" ,"SMARTG" ,"VLAN" ,"INNER" ,"SPEED" ,"ACESSE_NAME"};
         String[]rep={WanIp.trim(),smartG,A[searchIndex][2],Vlan.trim(),Speed,description}; 
		 for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]);
                 
            String[] D=config.split("\\s+");
            String d=D[(D.length)-1].trim();
           int comp=d.length();
           if(comp>31){
              findRemark.setText("Description exceed the limit by: "+(comp-31));findRemark.setHorizontalAlignment(JLabel.CENTER); 
           }else{
              findRemark.setText("Description exceed the limit by: 0");findRemark.setHorizontalAlignment(JLabel.CENTER);             
           }    
                 
         outputtext.append("\n\n     config t");
         outputtext.append("\n     "+vbui);                
         outputtext.append("\n  "+config);
         outputtext.append("\n"+"  !");
         outputtext.append("\n"+"  !");
         outputtext.append("\n    "+qinq);
         outputtext.append("\n         qinq "+A[searchIndex][2]+" second-dot1q "+Vlan.trim());
         outputtext.append("\n\n");
         updateTask(config);  
     }
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList vpnConfig(String description,String speed,String Vlan,String exVlan,String Wan, String Lan,String Sub,String Vrf,String Vrrp,String dataInterface){
    ArrayList<String> OP=new ArrayList<>();
        if(A[searchIndex][6].contains("smartgroup")){ 
        
        String vlan;String wan; String lan;String sub;String maxSpeed;String config="";
      String wanIp="0.0.0.0";String erAip="0.0.0.0";String erBip="0.0.0.0";String vrrpIp="0.0.0.0";
    
      if(Vlan.equals("")) vlan="0000";else vlan=Vlan;
      if(Wan.equals("")) wan="0.0.0.0";else wan=Wan;
      if(Lan.equals("")) lan="0.0.0.0";else lan=Lan;
      if(Sub.equals("")) sub="0.0.0.0";else sub=Sub;
//     String dataInterface=(A[searchIndex][6]+"."+A[searchIndex][2]+vlan);
       wanIp=hostIp(wan,29,4); 
       erAip=hostIp(wan,29,2); 
       erBip=hostIp(wan,29,3); 
       vrrpIp= hostIp(wan,29,1);
  if(speed(speed)==66)maxSpeed=num2str(15000);else maxSpeed=num2str(speed(speed)*200);
         String configB="";
    if(Vrrp.trim().equals("")&&(!Lan.equals("")&&!Lan.trim().equals("0.0.0.0"))){        
   config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $"
  ,"    $" 
  ,"    $" 
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ,"   $"
  ,"   $"
  ,"   $"
  ," ip route vrf VRF LANIP SUBNET WANIP"
  ,"   $"
  ,"   $"
  ,"   $"); 
        
    } 
    else if(Vrrp.trim().equals("")&&(Lan.equals("")||Lan.trim().equals("0.0.0.0"))){        
   config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $"
  ,"    $" 
  ,"    $" 
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ,"   $"
  ,"   $"
  ,"   $"); 
        
    }    
    else if(!(Vrrp.trim().equals(""))&&!(Lan.equals("")||Lan.trim().equals("0.0.0.0")))  {       
 config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ,"    $" 
  ,"    $" 
  ,"    $"
  ,"vrrp" 
  ,"  interface DATAINTERFACE "
    ,"    vrrp VRRP version 2 "
    ,"    vrrp VRRP ipv4 VRIP" 
    ,"    vrrp VRRP preempt" 
    ,"    vrrp VRRP priority VPPRIORITY" 
    ,"    vrrp VRRP timers advertise 3"
    ,"    vrrp VRRP send-mode all" 
    ,"    vrrp VRRP check-ttl" 
    ,"    vrrp VRRP accept" 

  ,"   $" 
  ,"   $" 
  ,"   $"
  ,"  ip route vrf VRF LANIP SUBNET WANIP"
  ,"   $"
  ,"   $"
  ,"   $"); 
} 
 else if(!(Vrrp.trim().equals(""))&&(Lan.equals("")||Lan.trim().equals("0.0.0.0")))  {       
 config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ,"    $" 
  ,"    $" 
  ,"    $"
  ,"vrrp" 
  ,"  interface DATAINTERFACE "
    ,"    vrrp VRRP version 2 "
    ,"    vrrp VRRP ipv4 VRIP" 
    ,"    vrrp VRRP preempt" 
    ,"    vrrp VRRP priority VPPRIORITY" 
    ,"    vrrp VRRP timers advertise 3"
    ,"    vrrp VRRP send-mode all" 
    ,"    vrrp VRRP check-ttl" 
    ,"    vrrp VRRP accept" 

  ,"   $"
  ,"   $"
  ,"   $"); 
}  
  configB=config;

 String []exp={"ERABIP","VRIP","WANIP" ,"VRF","VLAN" ,"INNER" ,"SPEED" ,"MAX2S" ,"LANIP" ,"SUBNET" ,"ACESSE_NAME","VRRP","DATAINTERFACE","VPPRIORITY"};
 String []rep={erAip,vrrpIp,wanIp,Vrf.trim(),exVlan,vlan,
                     num2str(speed(speed)),maxSpeed,networkIp(lan,sub),subNetMask(sub),description,Vrrp.trim(),dataInterface,"130" }; 
 
  String []expB={"ERABIP" ,"VRIP","WANIP" ,"VRF","VLAN" ,"INNER" ,"SPEED" ,"MAX2S" ,"LANIP" ,"SUBNET" ,"ACESSE_NAME","VRRP","DATAINTERFACE","VPPRIORITY"};
 String []repB={erBip,vrrpIp,wanIp,Vrf.trim(),exVlan,vlan,
                     num2str(speed(speed)),maxSpeed,networkIp(lan,sub),subNetMask(sub),description,Vrrp.trim(),dataInterface,"100" };
       
       for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]); 
       for (int i = 0; i < expB.length; i++) configB=configB.replace(expB[i],repB[i]); 

    OP.add(config);
    OP.add(configB); 
//     return finalConfig;
     
       
    }
    if(!A[searchIndex][6].contains("smartgroup")){
         String vlan;String wan; String lan;String sub;String maxSpeed;String config="";
      String wanIp="0.0.0.0";String erAip="0.0.0.0";String erBip="0.0.0.0";String vrrpIp="0.0.0.0";
    
      if(Vlan.equals("")) vlan="0000";else vlan=Vlan;
      if(Wan.equals("")) wan="0.0.0.0";else wan=Wan;
      if(Lan.equals("")) lan="0.0.0.0";else lan=Lan;
      if(Sub.equals("")) sub="0.0.0.0";else sub=Sub;
//     String dataInterface=(A[searchIndex][6]+"."+A[searchIndex][2]+vlan);
       wanIp=hostIp(wan,29,4); 
       erAip=hostIp(wan,29,2); 
       erBip=hostIp(wan,29,3); 
       vrrpIp= hostIp(wan,29,1);
  if(speed(speed)==66)maxSpeed=num2str(15000);else maxSpeed=num2str(speed(speed)*200);
         String configB="";
    if(Vrrp.trim().equals("")&&(!Lan.equals("")&&!Lan.trim().equals("0.0.0.0"))){        
   config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ," $"
  ," $"
  ," $"
,"     ip route vrf VRF LANIP SUBNET WANIP"
,""
,""
,""); 
        
    } 
    else if(Vrrp.trim().equals("")&&(Lan.equals("")||Lan.trim().equals("0.0.0.0"))){        
   config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ," $"
  ," $"
  ," $"); 
        
    }    
    else if(!(Vrrp.trim().equals(""))&&!(Lan.equals("")||Lan.trim().equals("0.0.0.0")))  {       
 config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ,"    $" 
  ,"    $" 
  ,"    $"
  ,"vrrp" 
  ,"  interface DATAINTERFACE "
    ,"    vrrp VRRP version 2 "
    ,"    vrrp VRRP ipv4 VRIP" 
    ,"    vrrp VRRP preempt" 
    ,"    vrrp VRRP priority VPPRIORITY" 
    ,"    vrrp VRRP timers advertise 3"
    ,"    vrrp VRRP send-mode all" 
    ,"    vrrp VRRP check-ttl" 
    ,"    vrrp VRRP accept" 

  ," $"
  ," $"
  ," $"
,"     ip route vrf VRF LANIP SUBNET WANIP"
,""
,""
,""); 
} 
 else if(!(Vrrp.trim().equals(""))&&(Lan.equals("")||Lan.trim().equals("0.0.0.0")))  {       
 config =String.join("\n"

,"   interface DATAINTERFACE"
  ,"        description ACESSE_NAME"
  ,"        ip vrf forwarding VRF "
  ,"        ip address ERABIP 255.255.255.248"
  ,"    $"
  ,"    $" 
  ,"    $" 
  ,"    $"
,"   qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
,"   vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"  
  ,"    $" 
  ,"    $" 
  ,"    $"
  ,"vrrp" 
  ,"  interface DATAINTERFACE "
    ,"    vrrp VRRP version 2 "
    ,"    vrrp VRRP ipv4 VRIP" 
    ,"    vrrp VRRP preempt" 
    ,"    vrrp VRRP priority VPPRIORITY" 
    ,"    vrrp VRRP timers advertise 3"
    ,"    vrrp VRRP send-mode all" 
    ,"    vrrp VRRP check-ttl" 
    ,"    vrrp VRRP accept" 

  ," $"
  ," $"
  ," $"); 
}  
  configB=config;

 String []exp={"ERABIP","VRIP","WANIP" ,"VRF","VLAN" ,"INNER" ,"SPEED" ,"MAX2S" ,"LANIP" ,"SUBNET" ,"ACESSE_NAME","VRRP","DATAINTERFACE","VPPRIORITY"};
 String []rep={erAip,vrrpIp,wanIp,Vrf.trim(),exVlan,vlan,
                     num2str(speed(speed)),maxSpeed,networkIp(lan,sub),subNetMask(sub),description,Vrrp.trim(),dataInterface,"130" }; 
 
 String []expB={"ERABIP" ,"VRIP","WANIP" ,"VRF","VLAN" ,"INNER" ,"SPEED" ,"MAX2S" ,"LANIP" ,"SUBNET" ,"ACESSE_NAME","VRRP","DATAINTERFACE","VPPRIORITY"};
 String []repB={erBip,vrrpIp,wanIp,Vrf.trim(),exVlan,vlan,
                     num2str(speed(speed)),maxSpeed,networkIp(lan,sub),subNetMask(sub),description,Vrrp.trim(),dataInterface,"100" };
       
       for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]); 
       for (int i = 0; i < expB.length; i++) configB=configB.replace(expB[i],repB[i]); 

    OP.add(config);
    OP.add(configB); 
//     return finalConfig;
     
    }
    return OP;
       }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  public ArrayList pConfig (String description,String speed,String Vlan,String exVlan,String Wan, String Lan,String Sub,String SMARTG,String dataInterface,String endAip,String endBip){
    ArrayList<String>OP=new ArrayList<>(); 
      if(A[searchIndex][6].contains("smartgroup")){  
      String vlan;String wan; String lan;String sub="",configB="",config="";
      if(Vlan.equals("")) vlan="0000";else vlan=Vlan;if(Wan.equals("")) wan="0.0.0.0";else wan=Wan;
      if(Lan.equals("")) lan="0.0.0.0";else lan=Lan;if(Sub.equals("")) sub="0.0.0.0";else sub=Sub;

// if(!Lan.equals("")&&!Lan.trim().equals("0.0.0.0")){     
 
     /*configB=String.join("\n"
,"   interface SMARTGP"
  ,"    qinq internal-vlan INNER external-vlan VLAN"
  ,"    ip address INTERIP INTIPSUB"
  ,"    description ACESSE_NAME"
  ,"  rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop"
  ,"  rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop"
  ,"  !"
  ,"  !"
,"   ip route LANIP SUBNET WANIP "
,""
,"                                        .......... ON-BRAS"
,""
,"     config t"
,"     ip route vrf DATA LANIP SUBNET ENDAIP         .........ON ER-A"
,""
,"     config t"
,"     ip route vrf DATA LANIP SUBNET ENDBIP          ..........ON ER-B\n\n");
 } */
 
     if(!Lan.equals("")||!Lan.trim().equals("0.0.0.0")){
 config=String.join("\n"
,"  interface DATAINTERFACE"
  ,"     description ACESSE_NAME"
  ,"     ip vrf forwarding DATA "
  ,"     ip address INTERIP INTIPSUB"
  ,"     $"
  ,"     $" 
  ,"     $"  
,"  qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
," vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"
  ,"   $" 
  ,"   $"
  ,"   $" 
   ,"   ip route vrf DATA LANIP SUBNET WANIP "
  ,"   $" 
  ,"   $");
 }
//// if(Lan.equals("")||Lan.trim().equals("0.0.0.0")){     
//// configB=String.join("\n"
////,"   interface SMARTGP"
////  ,"    qinq internal-vlan INNER external-vlan VLAN"
////  ,"    ip address INTERIP INTIPSUB"
////  ,"    description ACESSE_NAME"
////  ,"  rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop"
////  ,"  rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop"
////  ,"  !"
////  ,"  !");
////  }


 if(Lan.equals("")||Lan.trim().equals("0.0.0.0")){
 config=String.join("\n"
,"  interface DATAINTERFACE"
  ,"     description ACESSE_NAME"
  ,"     ip vrf forwarding DATA "
  ,"     ip address INTERIP INTIPSUB"
  ,"     $"
  ,"     $" 
  ,"     $"  
,"  qos"
  ,"    interface DATAINTERFACE"
  ,"    rate-limit input localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    rate-limit output localport cir SPEED kbps cbs MAX2S pir SPEED kbps pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
," vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"
  ,"   $" 
  ,"   $");
 }
  String maxSpeed;
  
  if(speed(speed)==66)maxSpeed=num2str(15000);else maxSpeed=num2str(speed(speed)*200);
   int temp=subIp(wan,3);String wanIp="0.0.0.0";String interIp="0.0.0.0";String interSub="0.0.0.0";
   if (temp>=64&&temp<=95){
    wanIp=hostIp(wan,30,2);
   interIp=hostIp(wan,30,1);
   interSub=subNetMask("30");
   }
    if (temp>=191&&temp<=255){
    
    wanIp=hostIp(wan,29,4);
    interIp=hostIp(wan,29,2);
    interSub=subNetMask("29");
    }
        String []exp={"WANIP" ,"INTERIP" ,"VLAN" ,"INNER" ,"SPEED" ,"MAX2S" ,"LANIP" ,"SUBNET" ,"ACESSE_NAME","INTIPSUB","DATAINTERFACE"};
        String []rep={wanIp,interIp,exVlan,vlan,
                     num2str(speed(speed)),maxSpeed,networkIp(lan,sub),subNetMask(sub),description,interSub,dataInterface }; 
       
       for (int i = 0; i < exp.length; i++) {
          // configB=configB.replace(exp[i],rep[i]);
           config=config.replace(exp[i],rep[i]);
       } 
      
       //OP.add(configB);
       OP.add(config);
//      return ("                  @T@A@M@I@R@U@     FOR PUBLIC --  BRAS --   CUSTOMER     @M@$@L@L@A@  \n\n\n"+
//              configB+
//              "\n                 @T@A@M@I@R@U@     FOR PUBLIC --  ER --  CUSTOMER      @M@$@L@L@A@  \n\n\n\n"+config);     
//
      }
       if(!A[searchIndex][6].contains("smartgroup")){ 
           String vlan;String wan; String lan;String sub="",configB="",config="";
      if(Vlan.equals("")) vlan="0000";else vlan=Vlan;if(Wan.equals("")) wan="0.0.0.0";else wan=Wan;
      if(Lan.equals("")) lan="0.0.0.0";else lan=Lan;if(Sub.equals("")) sub="0.0.0.0";else sub=Sub;

 if(!Lan.equals("")&&!Lan.trim().equals("0.0.0.0")){     
 configB=String.join("\n"
,"   interface SMARTGP"
  ,"    qinq internal-vlan INNER external-vlan VLAN"
  ,"    ip address INTERIP INTIPSUB"
  ,"    description ACESSE_NAME"
  ,"  rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action transmit exceed-action drop violate-action drop"
  ,"  rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action transmit exceed-action drop violate-action drop"
  ,"  !"
  ,"  !"
,"   ip route LANIP SUBNET WANIP "
,""
,"                                        .......... ON-BRAS"
,""
,"     config t"
,"     ip route vrf DATA LANIP SUBNET ENDAIP         .........ON ER-A"
,""
,"     config t"
,"     ip route vrf DATA LANIP SUBNET ENDBIP          ..........ON ER-B\n\n");
 }
 if(!Lan.equals("")||!Lan.trim().equals("0.0.0.0")){
 config=String.join("\n"
,"  interface DATAINTERFACE"
  ,"     description ACESSE_NAME"
  ,"     ip vrf forwarding DATA "
  ,"     ip address INTERIP 255.255.255.252"
  ,"     $"
  ,"     $" 
  ,"     $"  
,"  qos"
  ,"    interface DATAINTERFACE"
  ,"     rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"     rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
," vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"
  ,"   $" 
  ,"   $"
  ,"   $" 
   ,"   ip route vrf DATA  LANIP SUBNET WANIP "
  ,"   $" 
  ,"   $" 
  ,"   $");
 }
 if(Lan.equals("")||Lan.trim().equals("0.0.0.0")){     
 configB=String.join("\n"
,"   interface SMARTGP"
  ,"    qinq internal-vlan INNER external-vlan VLAN"
  ,"    ip address INTERIP INTIPSUB"
  ,"    description ACESSE_NAME"
  ,"  rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action transmit exceed-action drop violate-action drop"
  ,"  rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action transmit exceed-action drop violate-action drop"
  ,"  !"
  ,"  !");
  }
 if(Lan.equals("")||Lan.trim().equals("0.0.0.0")){
 config=String.join("\n"
,"  interface DATAINTERFACE"
  ,"     description ACESSE_NAME"
  ,"     ip vrf forwarding DATA "
  ,"     ip address INTERIP 255.255.255.252"
  ,"     $"
  ,"     $" 
  ,"     $"  
,"  qos"
  ,"    interface DATAINTERFACE"
  ,"     rate-limit input localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"     rate-limit output localport cir SPEED cbs MAX2S pir SPEED pbs MAX2S conform-action set-prec-transmit 0 exceed-action set-prec-transmit 0 violate-action drop statistical-share"
  ,"    $"
  ,"    $" 
  ,"    $"
," vlan-configuration"
  ,"   interface DATAINTERFACE"
    ,"   qinq internal-vlanid INNER external-vlanid VLAN"
  ,"   $" 
  ,"   $"
  ,"   $"  
  ,"  ip policy interface DATAINTERFACE route-map Toger"
  ,"   $" 
  ,"   $"
  ,"   $ ");
 }
  String maxSpeed;
 
  if(speed(speed)==66)maxSpeed=num2str(15000);else maxSpeed=num2str(speed(speed)*200);
   int temp=subIp(wan,3);String wanIp="0.0.0.0";String interIp="0.0.0.0";String interSub="0.0.0.0";
   if (temp>=64&&temp<=95){
    wanIp=hostIp(wan,30,2);
   interIp=hostIp(wan,30,1);
   interSub=subNetMask("30");
   }
    if (temp>=191&&temp<=255){
    wanIp=hostIp(wan,29,4);
    interIp=hostIp(wan,29,2);
    interSub=subNetMask("29");
    }
        String []exp={"ENDAIP" ,"ENDBIP" ,"WANIP" ,"INTERIP" ,"SMARTGP" ,"VLAN" ,"INNER" ,"SPEED" ,"MAX2S" ,"LANIP" ,"SUBNET" ,"ACESSE_NAME","INTIPSUB","DATAINTERFACE"};
        String []rep={endAip,endBip,wanIp,interIp,SMARTG,exVlan,vlan,
                     num2str(speed(speed)),maxSpeed,networkIp(lan,sub),subNetMask(sub),description,interSub,dataInterface }; 
       
       for (int i = 0; i < exp.length; i++) {
           configB=configB.replace(exp[i],rep[i]);
           config=config.replace(exp[i],rep[i]);
       } 
      
       OP.add(configB);
       OP.add(config);
//      return ("                  @T@A@M@I@R@U@     FOR PUBLIC --  BRAS --   CUSTOMER     @M@$@L@L@A@  \n\n\n"+
//              configB+
//              "\n                 @T@A@M@I@R@U@     FOR PUBLIC --  ER --  CUSTOMER      @M@$@L@L@A@  \n\n\n\n"+config);     
//
   
       }
  return OP;     
   
  } 
    
  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 
  public String hostIp(String IP,int sub,int hostIndex){     // only for CLASS C
   if(IP.trim().equals(""))  return ("0.0.0.0");
   else{   
  String NIP= networkIp(IP,sub);
  return (subIp(NIP,1)+"."+subIp(NIP,2)+"."+subIp(NIP,3)+"."+(subIp(NIP,4)+hostIndex)); }  
 }
//@@@@@@@@@@@@@@@@@@@@   
 public String hostIp(String IP,String sub,int hostIndex){     // only for CLASS C
   if(IP.trim().equals("")||sub.trim().equals(""))  return ("0.0.0.0");
   else{
  String NIP= networkIp(IP,sub);
  return (subIp(NIP,1)+"."+subIp(NIP,2)+"."+subIp(NIP,3)+"."+(subIp(NIP,4)+hostIndex)); }  
 } 
    
 //@@@@@@@@@@@@@@@@@@@@@@
    
  public String networkIp(String IP,int sub){ 
     
    if(IP.trim().equals(""))return "0.0.0.0";
    else{
     String m="";
     for (int i = 0; i < 4; i++) {
       m=m+(subIp(IP,i+1)&subIp(subNetMask(num2str(sub)),i+1))+".";          
     }     
       
  return m.trim().substring(0,m.trim().length()-1);
    }    
 }   
 //@@@@@@@@@@@@@@@@@@@@@
    
 public String networkIp(String IP,String sub){
      if(IP.trim().equals("")||sub.trim().equals(""))return "0.0.0.0";
      else{
     String m="";
     for (int i = 0; i < 4; i++) {
       m=m+(subIp(IP,i+1)&subIp(subNetMask(sub),i+1))+".";          
     }
     
  return m.trim().substring(0,m.trim().length()-1);
      }  
 } 
 
 //@@@@@@@@@@@@@@@@@@@@@@@@@@@2
 
 public int subIp(String IP,int ipIndex){
   
 if(IP.trim().equals("")) return 0;
 else{
    
ArrayList <Integer> LAN=indexOfSymbol(IP,".");
ArrayList <Integer> SUBIP=new ArrayList<>();
int a=LAN.get(0);int b=LAN.get(1);int c=LAN.get(2);
SUBIP.add(str2num(IP.substring(0,a)));
SUBIP.add(str2num(IP.substring(a+1,b)));
SUBIP.add(str2num(IP.substring(b+1,c)));
SUBIP.add(str2num(IP.substring(c+1,IP.length())));
return SUBIP.get(ipIndex-1);
 }
 }

   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     public ArrayList<ArrayList<String>> removeDoublicate(ArrayList<ArrayList<String>> report) {
       
  ArrayList<ArrayList<String>> data=new  ArrayList<ArrayList<String>>();       
  ArrayList<Integer> count=new ArrayList<Integer>();
  for (int i = 0; i < report.size(); i++) {
  for (int j = i+1; j < report.size(); j++) if (report.get(i).equals(report.get(j))) count.add(j);
            
          }
Collections.sort(count);
HashSet<Integer> hs=new HashSet<>(count);
ArrayList<Integer> A=new ArrayList<>();
ArrayList<Integer> B=new ArrayList<>();
for(int m:hs)A.add(m);
for (int i = 0; i < A.size(); i++) B.add(A.get(A.size()-1-i));      
for (int i = 0; i < B.size(); i++) report.get(B.get(i)).clear(); 
for(ArrayList<String> AA:report)if(!(AA.isEmpty()))data.add(AA);
   return data;   
  }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@

//@@@@@@@@@@@@@@@@@@@  
  public String num2str(int num){
    String A=Integer.toString(num);
    return A;
} 
  public int str2num(String str){
     int A=0;
try{
      A=Integer.parseInt(str);
}catch(NumberFormatException ex){}
    return A;
} 
public int bin2dec(String bin){
    int A=Integer.parseInt(bin,2);
return A;
}
public int bin2dec(int bin){
    int A=Integer.parseInt(num2str(bin),2);
return A;
}

//@@@@@@@@@@@@@@@@@@@@


public String subnet(String sub){
 if(sub.trim().equals("")) return " ";
 else{
    String subnetIn=subNetMask(sub);

int A=31-Integer.numberOfLeadingZeros(256-subIp(subnetIn,1));
int B=31-Integer.numberOfLeadingZeros(256-subIp(subnetIn,2));
int C=31-Integer.numberOfLeadingZeros(256-subIp(subnetIn,3));
int D=31-Integer.numberOfLeadingZeros(256-subIp(subnetIn,4));
 return num2str(32-(A+B+C+D));
 }
}



 //@@@@@@@@@@@@@@@@@@@@  
public String subNetMask(String sub){
    if (sub.trim().equals("")) return "";
    else{
    String subnetIn="";
//    isNumber();31-Integer.numberOfLeadingZeros(8)
if((sub.length()<7)&&(!(sub.contains("-")))&&(!(sub.contains(".")))){
int subN=Integer.parseInt(sub);
if(subN>32)return "INVALID SUBNET DETECTED!!!";
int[]m=new int[32];
        for (int i = 0; i < subN; i++) {
            m[i]=1;
        }
        String x="";
        for(int ind:m)x=x+ind;
   String X=x.trim();    

int A=Integer.parseInt(X.substring(0,8),2);
int B=Integer.parseInt(X.substring(8,16),2);
int C=Integer.parseInt(X.substring(16,24),2);
int D=Integer.parseInt(X.substring(24,32),2);

 subnetIn=(A+"."+B+"."+C+"."+D);

}
else if((sub.length()>=7)&&((indexOfSymbol(sub,".")).size()==3))
    subnetIn=sub;
//else if (0>(Integer.parseInt(sub))&&(Integer.parseInt(sub))>32)
    
else subnetIn="INVALID SUBNET DETECTED!!!";
return subnetIn;
    }
   }  
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@  
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList<Integer> indexOfSymbol (String SS,String ss){
      ArrayList<Integer> index=new ArrayList<>();
      int m=SS.indexOf(ss);
      while (m>0){
          index.add(m);
          m=SS.indexOf(".",m+1);
      } 
      return index;
    }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public String today(){
        String today=new SimpleDateFormat("ddMMMyy").format(cDate);
        return today;
    }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public ArrayList<String> accessNameFinder(String desc){
        String access="",name="";
        String []ACCESS=null; 
        ArrayList<String> accessName=new ArrayList<String>();
        ArrayList<String> AIO=new ArrayList<String>();
           ACCESS=(desc.replace("/"," ").replace("_"," ").replace("\""," ").replace("("," ").
                   replace( ")"," ").replace("["," ").replace("]"," ").replace("-"," ").
                   replace("."," ").replace("{"," ").replace("}"," ")).split("[a-zA-Z]+");
           for(String A:ACCESS)for(String B:A.split("\\s+"))AIO.add(B);
           for(String A:AIO) if(A.trim().length()>6) access= A.trim();   
          
         
           name=desc.replace(access,"").replace("/"," ").replace("_"," ").replace("-"," ").replace("."," ").trim();
           accessName.add(access);
           accessName.add(name);
       return accessName;   
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    //@@@@@@@@@@@@@@@@@@
    public String smartgroup(int x){
          String S=(qinqvbui100(x).split("\\s+"))[1];
        int ind=S.indexOf(".");
        String SMARTG=S.substring(0, ind+1);
        return SMARTG; 
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
    public int speed(String s)
    {int SPEED;int speed;
        if(s.equals(""))speed=0;
        else if(s.equals("64"))speed=66;
        else speed=Integer.parseInt(s);

        if (speed==512||speed==256||speed==66)SPEED=speed;else SPEED=speed*1024;
        return SPEED;
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@
    
     //@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
    public String speedN(String s,String WanIp)
    {int SPEED;int speed;String SP="";
        if(s.equals(""))speed=0;
        else if(s.equals("64"))speed=66;
        else speed=Integer.parseInt(s);

        if (speed==512||speed==256||speed==66)SPEED=speed;else SPEED=speed*1024;
       
        int temp=subIp(WanIp,3);
        int temp2=subIp(WanIp,2);
 
        if((temp>=0&&temp<=63)||
                (temp>=96&&temp<=111&&temp2!=85&&temp2!=151)||
                (temp>=96&&temp<=127&&temp2==151)||
                (temp>=128&&temp<=255&&temp2==85)){
             if(SPEED<1024) SP=getSpeedPrefix(WanIp)+"-"+speed+"K"; else SP=getSpeedPrefix(WanIp)+"-"+speed+"M";
        }
       
        if((WanIp.trim().substring(0,3).equals("196"))){
            if(SPEED<1024) SP=getSpeedPrefix(WanIp)+"-"+speed+"K-2"; else SP=getSpeedPrefix(WanIp)+"-"+speed+"M-2";
            
        }

        return SP;
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@
    
     //@@@@@@@@@@@@@@@@@@@@@@@@@
    
    
    public String speedNr(String s){
            String SPE="";
     if(s.contains("-")) { 
      int SPEED,speed,SP=0;

      if(s.split("[a-zA-Z]+").length>1)SP=Integer.parseInt(s.split("[a-zA-Z]+")[1].replace("-", ""));
      else SP=0;
    
     if(s.equals(""))SPEED=0;
     
     else SPEED=SP;
   
      if(SP!=0) SPE= Integer.toString(SPEED);
      else if(SP==0) SPE= "NA";
        }
      else SPE=s;
      
     return SPE;
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    public String qinqvbui100(int xx){
       String result="";
        if(A[xx][6].contains("smartgroup")){
        int index=xx;
        String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
              result=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][5]));
     }  else{      
              result=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][5]));
     } 
       }
      if(!A[xx][6].contains("smartgroup")) {
              int index=xx;
        String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
              result=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][5])+" bras");
     }  else{      
              result=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][5])+" bras");
     } 
      }
     return result;   
     
    }
     //@@@@@@@@@@@@@@@@@@@@@@@@
    
       public String qinqvbui101(int y){
     String qinq="";
     int index=y;
           if(A[y][6].contains("smartgroup")){ 
           
          
        String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][21])); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][21]));
     }  
      }
     if(!A[y][6].contains("smartgroup")) {
            String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][21])+" bras"); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][21])+" bras");
     } 
     }
        return qinq;   
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@
    
       public String qinqvbui200(int y){
     String qinq="";
     int index=y;
           if(A[y][6].contains("smartgroup")){ 
           
          
        String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][16])); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][16]));
     }  
      }
     if(!A[y][6].contains("smartgroup")) {
            String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][16])+" bras"); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][16])+" bras");
     } 
     }
        return qinq;   
    }
      
       //@@@@@@@@@@@@@@@@@@@@@@@@
    
       public String qinqvbui300(int y){
     String qinq="";
     int index=y;
           if(A[y][6].contains("smartgroup")){ 
           
          
        String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][22])); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][22]));
     }  
      }
     if(!A[y][6].contains("smartgroup")) {
            String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][22])+" bras"); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][22])+" bras");
     } 
     }
        return qinq;   
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@
    
       public String qinqvbui1700(int y){
        int index=y;String qinq="";
    
    if(A[y][6].contains("smartgroup")){ 
        String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][9])); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][9]));
     }
    }
      if(!A[y][6].contains("smartgroup")) {
          
          String tem =String.valueOf(A[index][4]).toLowerCase();
         String tem2="/";
         
     if (tem.contains(tem2)) {
             qinq=("interface gei_"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][9])+" bras"); 
     }  else{ 
             qinq=("interface smartgroup"+String.valueOf(A[index][4])+"."+String.valueOf(A[index][9])+" bras");
     }   
      }
        return qinq;   
    }
       
     //@@@@@@@@@@@@@@@@@@@@@@@
       
       public String[] dataColls(String [][] D,int dIndex){
           String[] newData=new String[D.length];
           for(int i=0;i<D.length;i++) newData[i]=(D[i][dIndex]);
           return newData;
       }
       
       //@@@@@@@@@@@@@@@@@@@@@@
         public ArrayList<ArrayList<String>> emptyRemover(ArrayList<ArrayList<String>> D){
//           }       ArrayList<ArrayList<String>> newData=new ArrayList<String>();
          for(int i=0;i<D.size();i++) if(D.get(D.size()-1-i).isEmpty())D.remove(D.size()-1-i);
           return D;
       }
       //@@@@@@@@@@@@@@@@@@@@@@@@
          public ArrayList<String> dataColls(ArrayList<ArrayList<String>> D,int dIndex){
           ArrayList<String> newData=new ArrayList<String>();
          for(int i=0;i<D.size();i++) newData.add(D.get(i).get(dIndex));
           return newData;
       }
       
//       @@@@@@@@@@@@@@@@@@@@@@@@@
        public int findIndex(String[] DD,String dd){          
              boolean b;String v;
              int sInd=-1;
              for ( int k=0;k<DD.length;k++)if(DD[k].equals(dd)) sInd=k;
            
             return sInd;    
        }
 
                  //@@@@@@@@@@@@@@@@@@@@@    
       public ArrayList<Integer> searchIndex(String[] DD,String dd,String EQ){          
              boolean b;String v;
              ArrayList<Integer> sInd=new ArrayList<>();
              for ( int k=0;k<DD.length;k++){
             v=String.valueOf(DD[k]).toLowerCase().trim();
             b=v.equals(dd.toLowerCase().trim());
             if(b==true) sInd.add(k);
               
              }
          return sInd;    
        }
//       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@

//@@@@@@@@@@@@@@@@@@@@@    
       public ArrayList<Integer> searchIndex(String[] DD,String dd){          
              boolean b;String v;
              ArrayList<Integer> sInd=new ArrayList<>();
              for ( int k=0;k<DD.length;k++){
             v=String.valueOf(DD[k]).toLowerCase();
             b=v.contains(dd.toLowerCase());
             if(b==true) sInd.add(k);
               
              }
          return sInd;    
        }
//       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
       
        public ArrayList<Integer> searchIndex(ArrayList<String> DD,String dd){          
              boolean b;String v;
              ArrayList<Integer> sInd=new ArrayList<>();
              for ( int k=0;k<DD.size();k++){                
             v=String.valueOf(DD.get(k)).toLowerCase();
             b=v.contains(dd.toLowerCase());
             if(b==true) sInd.add(k);
               
              }
          return sInd; 
            
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
         public ArrayList<Integer> searchIndex(ArrayList<String> DD,String dd,String mm){          
              boolean b;String v;
              ArrayList<Integer> sInd=new ArrayList<>();
              for ( int k=0;k<DD.size();k++){                
             v=String.valueOf(DD.get(k)).toLowerCase();
             b=((v.contains(dd.toLowerCase()))&&(v.contains(mm.toLowerCase())));
             if(b==true) sInd.add(k);
               
              }
          return sInd; 
            
        }
         
    public ArrayList<Integer> searchIndexor(ArrayList<String> DD,String dd,String mm){          
              boolean b;String v;
              ArrayList<Integer> sInd=new ArrayList<>();
              for ( int k=0;k<DD.size();k++){                
             v=String.valueOf(DD.get(k)).toLowerCase();
             b=((v.contains(dd.toLowerCase()))||(v.contains(mm.toLowerCase())));
             if(b==true) sInd.add(k);
               
              }
          return sInd; 
            
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        public ArrayList <String> resume(String SS,String svlan){
            
            ArrayList<String> tData = new ArrayList<>(); 
           
        // internet 
        
        
         if (!(SS.contains("second-vlan"))&&(SS.contains("ip host")) ) { 
//            String textotr="";
           OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
            String textotr=OTR.get(0);
             ArrayList<String> finalResumed = new ArrayList<>(); 
            String [] tokin=textotr.split("\\s+");
            ArrayList<String> tok=new ArrayList<>();
            for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));
            String []sVlan=null;             
            
           sVlan=svlan.split("[a-zA-Z]+");
               if(tok.get(3).contains("slot"))
            {
               String g=tok.get(14).replace(svlan, "");
               tok.add(9,"second-vlan");
               tok.add(10,sVlan[sVlan.length-1]);
               tok.add(g);
               tok.remove(16); 
            }
               
            if(tok.get(3).contains("smartgroup"))
            {
               String p=tok.get(11).replace(svlan, "");
               tok.add(6,"second-vlan");
               tok.add(7,sVlan[sVlan.length-1]);  
               tok.add(p);
               tok.remove(13); 
            }
        
           String Resume=""; String m1="";
           for(String tokIng:tok) Resume= (Resume+tokIng+" ");
                           
          finalResumed = new ArrayList<>();
           int temp=subIp(tok.get(2),3);          
           int temp2=subIp(tok.get(2),2);          
//        if(temp>=0&&temp<=63) m1="interface vbui100";  
//        if(temp>=96&&temp<=111)m1="interface vbui200";
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        
        if (tok.get(2).substring(0,3).equals("196"))  m1="interface vbui1700" ;

            updateTask(Resume);
            finalResumed.add("config t"); 
            finalResumed.add(m1);
            finalResumed.add("no ip host "+tok.get(2));
            finalResumed.add(Resume);           

          tData= finalResumed; 
        }
          if(svlan.equals("")&&!(SS.contains("second-vlan"))&&(SS.contains("ip host")))
             tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   Select   The  Blocked  SECOND-VLAN   First !!!\n\n"}));
           
         if ((SS.contains("second-vlan"))&&(SS.contains("ip host")) ){ 
             tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Active\n\n"}));
            }
        //////////////////////////////////////////////////////////////// 
        
        if (!(SS.contains("sec-vlan"))&&(SS.contains("ip-host")) ) { 
//            String textotr="";
           OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
            String textotr=OTR.get(0);
             ArrayList<String> finalResumed = new ArrayList<>(); 
            //String [] tokin=textotr.split("\\s+");
            ArrayList<String> tok=new ArrayList<>(Arrays.asList(textotr.split("\\s+")));
            //for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));
            String []sVlan=null;             
 // ip-host description 41100054151_SEBLEWENGEL 10.150.57.210 smartgroup1.1004 vlan 2615 sec-vlan 1115 author-temp-name YK-512K  
           sVlan=svlan.split("[a-zA-Z]+");
               if(tok.get(3).contains("slot"))
            {
               String g=tok.get(14).replace(svlan, "");
               tok.add(9,"second-vlan");
               tok.add(10,sVlan[sVlan.length-1]);
               tok.add(g);
               tok.remove(16); 
            }
               
            if(tok.get(4).contains("smartgroup"))
            {
               String p=tok.get(2).replace(svlan, "");
               tok.add(7,"sec-vlan");
               tok.add(8,sVlan[sVlan.length-1]);  
               tok.remove(2);
               tok.add(2,p);
               //tok.remove(13); 
            }
        
           String Resume=""; String m1="";
           for(String tokIng:tok) Resume= (Resume+tokIng+" ");
                           
          finalResumed = new ArrayList<>();
           int temp=subIp(tok.get(3),3);   
           int temp2=subIp(tok.get(3),2); 
        // update301220   
           
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        if (tok.get(3).substring(0,3).equals("196"))  m1="interface vbui1700" ;

            updateTask(Resume);
            finalResumed.add("config t"); 
            finalResumed.add("vbui-configuration");
            finalResumed.add(m1);
            finalResumed.add("no ip-host "+tok.get(3));
            finalResumed.add(Resume);           

          tData= finalResumed; 
        }
          if(svlan.equals("")&&!(SS.contains("sec-vlan"))&&(SS.contains("ip-host")))
             tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   Select   The  Blocked  SECOND-VLAN   First !!!\n\n"}));
           
         if ((SS.contains("sec-vlan"))&&(SS.contains("ip-host")) ){ 
             tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Active\n\n"}));
            }
        //////////////////////////////////////////////////////////////
        
         // public
          if((SS.contains("internal-vlan "))&&(SS.contains("external-vlan "))&&(SS.contains("shutdown"))){
           
           tData = new ArrayList<>();
           ArrayList<String> pData=ppParamFind(SS);            
           tData.add("config t");
           tData.add("interface "+pData.get(0));
           tData.add("no shutdown");
           tData.add("!");
           tData.add("!");
           tData.add("\n"); 
           
          
          }
           if((SS.contains("internal-vlan "))&&(SS.contains("external-vlan "))&&!(SS.contains("shutdown"))){
              
          tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Active"+"\n\n\n"}));
          }
        

        // vpn   
        /////////////////////////////////////////////////////////////////////////////////////  
       if(((SS.contains("ip vrf forwarding "))&&(SS.contains("shutdown")))||
    ((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&(SS.contains("shutdown")))){
//        if((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&(SS.contains("shutdown"))){
           
           tData = new ArrayList<>();String ipRoute="";
           ArrayList<String> vpnData=vpnParamFind(SS); 
          if(SS.contains("vrrp")) {
           tData.add("             ER - A\n\n");
           tData.add("config t");
           tData.add("interface "+vpnData.get(0));
           tData.add("no shutdown");
           tData.add("!");
           tData.add("!");
           tData.add("\n               ER - B\n\n");
           tData.add("config t");
           tData.add("interface "+vpnData.get(0));
           tData.add("no shutdown");
           tData.add("!");
           tData.add("!");   
          }
           if(!(SS.contains("vrrp"))) {
           tData.add("             ER - A/B \n\n");
           tData.add("config t");
           tData.add("interface "+vpnData.get(0));
           tData.add("no shutdown");
           tData.add("!");
           tData.add("!");   
          }
           tData.add("\n");              
          
       
       }
       if(((SS.contains("ip vrf forwarding "))&&!(SS.contains("shutdown")))||
           ((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&!(SS.contains("shutdown")))){
              
          tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Active"+"\n\n\n"}));
          }
//          if((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&!(SS.contains("shutdown"))){
   
        ///////////////////////////////////////////////////////////////////////////////////
        
        
         return tData;
        }
        
     public ArrayList  findAllVpnData( String srch){
      String host="", host2="";
      host=("hostname "+String.valueOf(A[searchIndex][17])+"-ER-DATA-");
      host2=("-"+String.valueOf(A[searchIndex][17])+"-PE-");//-YK-PE-
       
     ArrayList<String> iphostData1=iphostDataA;
     ArrayList<Integer> findAllIndex=new ArrayList<>();	 
     ArrayList<String> allData=new ArrayList<>();    
     findAllIndex= searchIndex(iphostData1,srch);
    
  if(!(findAllIndex.isEmpty())) {
        for(int A:findAllIndex){
        int fIndex=A;   
           
       if(!(iphostData1.get(fIndex).contains("ip host")||iphostData1.get(fIndex).contains("ip-host")||iphostData1.get(fIndex).contains("ip route"))&&
               (findErHostName(A).contains(host)||(findErHostName(A).contains("hostname ")&&findErHostName(A).contains(host2)))){
           int initiaL=-1; int finaL=-1;
           for (int i =fIndex ; i < iphostData1.size(); i++) {
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      finaL=i;break;
                  }                  
           }
            for (int i =fIndex ; i >=0; i--) {
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      initiaL=i;break;}
                   }
            
           if(initiaL>-1&&finaL>0) for (int i = initiaL; i <=finaL; i++)allData.add(iphostData1.get(i));
                          
        }
       }         
     }
       
return allData;	 
}
       //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     public ArrayList <String> publicIpFinder(String wanIp,String wanSubnet){
                 
            ArrayList<String> tData=new ArrayList<>();
      
           ArrayList<Integer>  rIndex=new ArrayList <>();
          rIndex=searchIndex(iphostDataA,wanIp,"ip route");
             for (int i = 0; i < rIndex.size(); i++) {

                   String [] tokin=iphostDataA.get(rIndex.get(i)).split("\\s+");

                     ArrayList<String> tok=new ArrayList<>();
                     String subN="",publicIp="";

                     for(String h:tokin)tok.add(h);            
                     tok.removeAll(Arrays.asList("", null));

                     subN=tok.get(tok.size()-2);

                     if(networkIp(tok.get(tok.size()-1),wanSubnet).equals(networkIp(wanIp,wanSubnet))){

                         publicIp=tok.get(2)+" / "+subnet(subN);
                         tData.add(publicIp);

                     }
                           
                   }          

                 
                  return tData;
             }
           
           // VPN

   public ArrayList <String> lanIpFinder(String wan,String sub,String vrf){
                 
            ArrayList<String> tData=new ArrayList<>();
           String lanIp="";
           int vSub=-1;
               
           ArrayList<Integer>  rIndex=new ArrayList <>();

         
           if(sub.trim().equals("255.255.255.248"))vSub=4;
           if(sub.trim().equals("255.255.255.252"))vSub=2;

             rIndex=searchIndex(iphostDataA,hostIp(wan,sub,vSub),"ip route vrf "+vrf);

               for (int i = 0; i < rIndex.size(); i++) {

                   String [] tokin=iphostDataA.get(rIndex.get(i)).split("\\s+");
                     ArrayList<String> tok=new ArrayList<>();
                     
                     for(String h:tokin)tok.add(h);            
                     tok.removeAll(Arrays.asList("", null));
                    
                    
                     if(networkIp(tok.get(tok.size()-1),sub).equals(networkIp(wan,sub))){
                         lanIp=tok.get(4)+" / "+subnet(tok.get(5));
                         tData.add(lanIp);
                         
                         
                         }
                     
                    }
                 
                 
           
            return tData; 
        }
        
//       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ GAP_TIME_FOR_NEW_CONF
        public ArrayList <String> termination(String SS){
                 
            ArrayList<String> tData=new ArrayList<>();
        // IP HOST CUSTOMERS   
        
             if (SS.contains("ip host") ) { 
            tData = new ArrayList<>();   
           OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           String textot;
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
           textot=OTR.get(0);   
            String [] tokin=textot.split("\\s+");
            ArrayList<String> tok=new ArrayList<>();
            for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));   
          String m1="";  
        int temp=subIp(tok.get(2),3);          
        int temp2=subIp(tok.get(2),2); 
        // update301220   
           
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        
        
        
        if (tok.get(2).substring(0,3).equals("196"))  m1="interface vbui1700" ;
            tData.add("config t"); 
            tData.add(m1);
            tData.add("no ip host "+tok.get(2));
            
           }
        
           if (SS.contains("ip-host") ) { 
            tData = new ArrayList<>();   
           OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           String textot;
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
           textot=OTR.get(0);   
           // String [] tokin=textot.split("\\s+");
            ArrayList<String> tok=new ArrayList<>(Arrays.asList(textot.split("\\s+")));
           // for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));   
          String m1="";  
        int temp=subIp(tok.get(3),3);          
//        if(temp>=0&&temp<=63) m1="interface vbui100";  
//        if(temp>=96&&temp<=111)m1="interface vbui200";
     int temp2=subIp(tok.get(3),2); 
        // update301220   
           
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        if (tok.get(3).substring(0,3).equals("196"))  m1="interface vbui1700" ;
            tData.add("config t"); 
            tData.add("vbui-configuration");
            tData.add(m1);
            tData.add("no ip-host "+tok.get(3));
            
           }
           
           // PUBLIC
           if((SS.contains("internal-vlan "))&&(SS.contains("external-vlan "))){
           tData = new ArrayList<>();String ipRoute="";
           ArrayList<String> pData=ppParamFind(SS); 
            tData.add("------------ BRAS SIDE---------");
           tData.add("\n");
           tData.add("config t");
           tData.add("no interface "+pData.get(0));
           tData.add("!");
           tData.add("!");
           ArrayList<Integer>  rIndex=new ArrayList <>();
           ArrayList<String>  ipRouteEr=new ArrayList <>();
             rIndex=searchIndex(iphostDataA,pData.get(3),"ip route");
             
             
                    for (int i = 0; i < rIndex.size(); i++) {
                   String [] tokin=iphostDataA.get(rIndex.get(i)).split("\\s+");
                     ArrayList<String> tok=new ArrayList<>();String subN;
                     for(String h:tokin)tok.add(h);            
                     tok.removeAll(Arrays.asList("", null));
                     subN=pData.get(pData.size()-1);
                     if(networkIp(tok.get(tok.size()-1),subN).equals(networkIp(pData.get(3),pData.get(pData.size()-1)))){
                         ipRoute=iphostDataA.get(rIndex.get(i));
                         tData.add("no "+ipRoute);}
                           
                     }
             
                   for (int i = 0; i < rIndex.size(); i++) {
                   String [] tokin=iphostDataA.get(rIndex.get(i)).split("\\s+");
                     ArrayList<String> tok=new ArrayList<>();String subN;
                     for(String h:tokin)tok.add(h);            
                     tok.removeAll(Arrays.asList("", null));
                     subN=pData.get(pData.size()-1);
                     if(networkIp(tok.get(tok.size()-1),subN).equals(networkIp(pData.get(3),pData.get(pData.size()-1)))){
//                         ipRoute=iphostDataA.get(rIndex.get(i));
//                         tData.add("no "+ipRoute);
                       if(i==0){
                        tData.add("\n");
                        tData.add("------------ ER SIDE---------\n");
                       }
                         
                         ArrayList<Integer> ipRer=new ArrayList<>();
                         ipRer=searchIndex(iphostDataA,("ip route vrf DATA "+tok.get(2)+" "+tok.get(3)));
                       for (int j = 0; j < ipRer.size(); j++) ipRouteEr.add(iphostDataA.get(ipRer.get(j))); 
                         
                     }
                     
                    }
               if(ipRouteEr.size()>0) for(String C:ipRouteEr)tData.add("\n\n   config t\n   no "+C);
              
               tData.add("\n");
                 } 
           
           // VPN
          if((SS.contains("ip vrf forwarding "))||((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid ")))){
           tData = new ArrayList<>();String ipRoute="";int vSub=-1;
           ArrayList<String> vpnData=vpnParamFind(SS); 
            tData.add("------------ ER SIDE---------");
           tData.add("\n");
           tData.add("   config t");
           tData.add("   no interface "+vpnData.get(0));
           tData.add("   !");
           tData.add("   !");
           ArrayList<Integer>  rIndex=new ArrayList <>();
           ArrayList<String>  ipRouteEr=new ArrayList <>();
           if(SS.contains("255.255.255.248"))vSub=4;
           if(SS.contains("255.255.255.252"))vSub=2;

             rIndex=searchIndex(iphostDataA,hostIp(vpnData.get(3),vpnData.get(vpnData.size()-1),vSub),"ip route vrf "+vpnData.get(2));
               for (int i = 0; i < rIndex.size(); i++) {
                   String [] tokin=iphostDataA.get(rIndex.get(i)).split("\\s+");
                     ArrayList<String> tok=new ArrayList<>();String subN;
                     for(String h:tokin)tok.add(h);            
                     tok.removeAll(Arrays.asList("", null));
                     subN=vpnData.get(vpnData.size()-1);
                    
                     if(networkIp(tok.get(tok.size()-1),subN).equals(networkIp(vpnData.get(3),vpnData.get(vpnData.size()-1)))){
                         ipRoute=iphostDataA.get(rIndex.get(i));
                         tData.add("   config t\n      no "+ipRoute+"\n"); 
                         }
                     
                    }
                                        ArrayList<Integer> ipRer=new ArrayList<>();
                         ipRer=searchIndex(iphostDataA,("ip policy interface "+vpnData.get(0)),"route-map Toger");
                         if(ipRer.size()>0){
                             tData.add("\n");                         
                             
                            for(Integer B:ipRer)ipRouteEr.add(iphostDataA.get(B)); 
                         }


               if(ipRouteEr.size()>0){
//               tData.add("config t");
                 for(String C:ipRouteEr)tData.add("   config t\n      no "+C);
               }
               tData.add("\n");
                 }
           
            return tData; 
        }
 //     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ TERMINATION        
      public ArrayList <String> twoWayBlock(String SS){
         ArrayList<String> tData=new ArrayList<>();
          // internet
          
          
             if ((SS.contains("second-vlan"))&&(SS.contains("ip host")) ) {  
           String textot="";
           OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
            textot=OTR.get(0);
            String [] tokin=textot.split("\\s+");
            ArrayList<String> tok=new ArrayList<>();
            for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));
            String svlan="";
               if(tok.get(3).contains("slot"))
            {
               svlan=tok.get(10);
               String g=(tok.get(16)+"_b"+svlan);
               tok.remove(16);
               tok.remove(10);tok.remove(9);
               tok.add(g);
                
            }
               
            if(tok.get(3).contains("smartgroup"))
            {
                svlan=tok.get(7);
                String p=(tok.get(13)+"_b"+svlan);
                tok.remove(13); 
                tok.remove(7);  tok.remove(6); 
                tok.add(p); 
            }
        
           String Block=""; String m1="";
           for(String tokIng:tok) Block= (Block+tokIng+" ");
                           
           ArrayList<String> finalBlocked = new ArrayList<>();
         int temp=subIp(tok.get(2),3);          
//        if(temp>=0&&temp<=63) m1="interface vbui100";  
//        if(temp>=96&&temp<=111)m1="interface vbui200";
        int temp2=subIp(tok.get(2),2); 
        // update301220   
           
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        if (tok.get(2).substring(0,3).equals("196"))  m1="interface vbui1700" ;
//        if (tok.get(2).substring(0,3).equals("196"))  m1="interface vbui1700" ;
//           if (!(tok.get(2).substring(0,3).equals("196"))) m1="interface vbui100" ;
             updateTask(Block);
            finalBlocked.add("config t"); 
            finalBlocked.add(m1);
            finalBlocked.add("no ip host "+tok.get(2));
            finalBlocked.add(Block);
          
            tData= finalBlocked; 
            
            
           String[] D=Block.split("\\s+");
           String d=D[(D.length)-1];
           int comp=d.length();
           if(comp>31){
              findRemark.setText("Description exceed the limit by: "+(comp-31));
              findRemark.setHorizontalAlignment(JLabel.CENTER); 
           }else{
              findRemark.setText("Description exceed the limit by: 0");
              findRemark.setHorizontalAlignment(JLabel.CENTER); 
              
           }
        
       }
         if (!(SS.contains("second-vlan"))&&(SS.contains("ip host")) )
               tData=new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is already Blocked\n\n"}));
           
         //////////////////////////////////////////////////////////////////////////////////////////// 
          if ((SS.contains("sec-vlan"))&&(SS.contains("ip-host")) ) {  
           String textot="";
           OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
            textot=OTR.get(0);
           // String [] tokin=textot.split("\\s+");
            ArrayList<String> tok=new ArrayList<>(Arrays.asList(textot.split("\\s+")));
            //(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Blocked"}))
           // for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));
            String svlan="";
           
            if(tok.get(4).contains("smartgroup"))
            {
                svlan=tok.get(8);
                String p=(tok.get(2)+"_b"+svlan);
                tok.remove(8); 
                tok.remove(7); 
                tok.remove(2); 
                tok.add(2, p); 
            }
        
           String Block=""; 
           String m1="";
           for(String tokIng:tok) Block= (Block+tokIng+" ");
                           
           ArrayList<String> finalBlocked = new ArrayList<>();
         int temp=subIp(tok.get(3),3);          
//        if(temp>=0&&temp<=63) m1="interface vbui100";  
//        if(temp>=96&&temp<=111)m1="interface vbui200";
     int temp2=subIp(tok.get(3),2); 
        // update301220   
           
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        if (tok.get(3).substring(0,3).equals("196"))  m1="interface vbui1700" ;
//        if (tok.get(2).substring(0,3).equals("196"))  m1="interface vbui1700" ;
//           if (!(tok.get(2).substring(0,3).equals("196"))) m1="interface vbui100" ;
             updateTask(Block);
            finalBlocked.add("config t"); 
            finalBlocked.add("vbui-configuration");
            finalBlocked.add(m1);
            finalBlocked.add("no ip-host "+tok.get(3));
            finalBlocked.add(Block);
          
            tData= finalBlocked; 
            
            
           String[] D=Block.split("\\s+");
           String d=D[2];
           int comp=d.length();
           if(comp>31){
              findRemark.setText("Description exceed the limit by: "+(comp-31));
              findRemark.setHorizontalAlignment(JLabel.CENTER); 
           }else{
              findRemark.setText("Description exceed the limit by: 0");
              findRemark.setHorizontalAlignment(JLabel.CENTER); 
              
           }
           
         
       }
        
            if (!(SS.contains("sec-vlan"))&&(SS.contains("ip-host")) )
               tData=new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is already Blocked\n\n"}));
         
            
           //////////////////////////////////////////////////////////////////////
            //public
          if((SS.contains("internal-vlan "))&&(SS.contains("external-vlan "))&&!(SS.contains("shutdown"))){
           
           tData = new ArrayList<>();String ipRoute="";
           ArrayList<String> pData=ppParamFind(SS); 
           
           tData.add("config t");
           tData.add("interface "+pData.get(0));
           tData.add(" shutdown");
           tData.add("!");
           tData.add("!");
           tData.add("\n");              
          }
          if((SS.contains("internal-vlan "))&&(SS.contains("external-vlan "))&&(SS.contains("shutdown"))){
              
          tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Blocked\n\n"}));
          }
        

        // vpn   
        /////////////////////////////////////////////////////////////////////////////////////  
       
//        if((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&!(SS.contains("shutdown"))){
        if((SS.contains("ip vrf forwarding "))&&!(SS.contains("shutdown"))||
        ((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&!(SS.contains("shutdown")))){
           
           tData = new ArrayList<>();String ipRoute="";
           ArrayList<String> vpnData=vpnParamFind(SS); 
          if(SS.contains("vrrp")) {
           tData.add("             ER - A\n\n");
           tData.add("config t");
           tData.add("interface "+vpnData.get(0));
           tData.add(" shutdown");
           tData.add("!");
           tData.add("!");
           tData.add("\n               ER - B\n\n");
           tData.add("config t");
           tData.add("interface "+vpnData.get(0));
           tData.add(" shutdown");
           tData.add("!");
           tData.add("!");   
          }
           if(!(SS.contains("vrrp"))) {
           tData.add("             ER - A/B\n\n");
           tData.add("config t");
           tData.add("interface "+vpnData.get(0));
           tData.add(" shutdown");
           tData.add("!");
           tData.add("!");
   
          }
           tData.add("\n");              
          }
//          if((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&(SS.contains("shutdown"))){
    if((SS.contains("ip vrf forwarding "))&&(SS.contains("shutdown")) ||
    ((SS.contains("internal-vlanid "))&&(SS.contains("external-vlanid "))&&(SS.contains("shutdown")))){
              
          tData= new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Blocked\n\n"}));
          }
        ///////////////////////////////////////////////////////////////////////////////////
          
        return tData;
      }
       
       public ArrayList <String> changeVlan(String SS,String sVlan){
            ArrayList<String> data=bbParamFind(SS);
            ArrayList<String> finalVlan = new ArrayList<>(); 
            String wanIp=data.get(0);
            String Interface="",descr="";
            boolean VV=(SS.contains("sec-vlan")||SS.contains("second-vlan"));           
           if (VV ) {   
               String exVlan="";
               ArrayList vlanInd=new ArrayList <>();
               Interface=data.get(1);
               String m2=""; String cVlan=""; String m1="";
               int temp=-1,temp2=-1;
       if(SS.contains("ip host")) {    
          if(SS.contains("slot")&&SS.contains("port"))
            {
             temp=subIp(midString(SS,"ip host","slot"),3);
            temp2=subIp(midString(SS,"ip host","slot"),2); 
            if(SS.contains("ip host 196."))  m1="interface vbui1700" ;            
//             if(temp>=0&&temp<=63) m1="interface vbui100" ;
//             if(temp>=96&&temp<=111) m1="interface vbui200" ;
                 
        // update301220   
           
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
             descr=data.get(7);
                   int slotindex=-1; 
                   exVlan=data.get(4);
                   String slotPortStr=(data.get(2)+"/"+data.get(3));
                   vlanInd=searchIndex(dataColls(A,2),exVlan);
           if(!(vlanInd.size()==0)){
              for (int j = 0; j < vlanInd.size(); j++) {
                  if(String.valueOf(A[(int)vlanInd.get(j)][4]).equals(slotPortStr))
                  slotindex=j;
              } 
             
             int slotPort=(int)vlanInd.get(slotindex); 
             if (SS.contains("ip host 196.")) m2=qinqvbui1700(slotPort) ;
//             if(temp>=0&&temp<=63)m2=qinqvbui100(slotPort);
//             if(temp>=96&&temp<=111)m2=qinqvbui200(slotPort); 
             
        if((temp>=0&&temp<=63)&&(temp2!=85)) m2=qinqvbui100(slotPort);
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m2=qinqvbui200(slotPort);
        if((temp>=128&&temp<=254)&&(temp2==85))m2=qinqvbui101(slotPort);
        if((temp>=96&&temp<=127)&&(temp2==151))m2=qinqvbui300(slotPort);
              
//             if (!(SS.contains("ip host 196."))) m2=qinqvbui100(slotPort);             
             }
            cVlan="ip host "+wanIp+" "+Interface+" vlan "+data.get(4)+" second-vlan "+sVlan+" up-rate "+data.get(6)+" down-rate "+data.get(6)+" description "+descr;
            }
          if(SS.contains("smartgroup")) {
                temp=subIp(midString(SS,"ip host","smartgroup"),3);
                temp2=subIp(midString(SS,"ip host","smartgroup"),2);
                descr=data.get(5);
                m2=" interface "+data.get(1)+" bras";
             if(SS.contains("ip host 196.")) m1="interface vbui1700" ;            
//             if(temp>=0&&temp<=63) m1="interface vbui100" ;
//             if(temp>=96&&temp<=111) m1="interface vbui200" ;
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
                exVlan=data.get(2);
                cVlan="ip host "+wanIp+" "+Interface+" vlan "+data.get(2)+" second-vlan "+sVlan+" up-rate "+data.get(4)+" down-rate "+data.get(4)+" description "+descr;
            }
          
           if(m2.equals("")){
             finalVlan.add("config t");finalVlan.add(m1);finalVlan.add("no ip host "+data.get(0));finalVlan.add(cVlan);
            }
           else{
            finalVlan.add("config t");
            finalVlan.add(m1);
            finalVlan.add("no ip host "+data.get(0));
            finalVlan.add(cVlan);
            finalVlan.add("  !");
            finalVlan.add("  !");
            
            if(!m2.trim().equals(qinqFinder(exVlan,sVlan,getIpAlloc(data.get(0))).trim())&&
                    (!qinqFinder(exVlan,sVlan,getIpAlloc(data.get(0))).trim().equals(""))){
            
            finalVlan.add("  "+qinqFinder(exVlan,sVlan,getIpAlloc(data.get(0))).trim());
            finalVlan.add("    no qinq "+exVlan+" second-dot1q "+sVlan);
            finalVlan.add("  !");
            finalVlan.add("  !");
            }
            finalVlan.add(m2);
            finalVlan.add("    qinq "+exVlan+" second-dot1q "+sVlan);
            finalVlan.add("  !");
            finalVlan.add(m1);
            finalVlan.add(cVlan);
            
            
           } 
       }
          
        if(SS.contains("ip-host")) { 
          
          if(SS.contains("smartgroup")) {
                temp=subIp(data.get(0),3);
                temp2=subIp(data.get(0),2);
                descr=data.get(5);
                m2=" interface "+data.get(1);
              
             
             if(data.get(0).substring(0,4).contains("196.")) m1="interface vbui1700" ;            
//             if(temp>=0&&temp<=63) m1="interface vbui100" ;
//             if(temp>=96&&temp<=111) m1="interface vbui200" ;
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
                exVlan=data.get(2);
                cVlan="ip-host description "+descr+" "+wanIp+" "+Interface+" vlan "+data.get(2)+" sec-vlan "+sVlan+
                        " author-temp-name "+data.get(4);
            }    
                 

            finalVlan.add("config t");
            finalVlan.add("vbui-configuration");
            finalVlan.add(m1);
            finalVlan.add("no ip-host "+data.get(0));
            finalVlan.add(cVlan);
            finalVlan.add("  $");
            finalVlan.add("  $");
            finalVlan.add("vlan-configuration");
            finalVlan.add(m2);
            finalVlan.add("    qinq range internal-vlan-range "+sVlan+" external-vlan-range "+exVlan);
            finalVlan.add("  $");
            //finalVlan.add("vbui-configuration");
            //finalVlan.add(m1);
           // finalVlan.add(cVlan);
         } 
            return finalVlan; 
           }
          
           return new ArrayList<>(Arrays.asList(new String[]{"\n\n"+"   The current Customer is Blocked"}));
        }
        
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
       
//       public ArrayList <String> changeSpeed(String SS,String speed){
       public String changeSpeed(String SS,String speed){
     ArrayList<String> finalSpeedC = new ArrayList<>(); 
     String fs="";
     
     if(SS.contains("ip host")) {   
           String SPEED;int Speed;
            Speed=Integer.parseInt(speed);
           if (Speed==512||Speed==256||Speed==64||Speed==1024||Speed==2048)SPEED=Speed+"";else SPEED=Speed*1024+"";
           
           String SSS;
           OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
           SSS=OTR.get(0);
            String [] tokin=SSS.split("\\s+");
            ArrayList<String> tok=new ArrayList<>();
            for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));            
            ArrayList<Integer> upRateIndex= searchIndex(tok,"up-rate");
            
           tok.remove(upRateIndex.get(0)+1); 
           tok.remove(upRateIndex.get(0)+2); 
           tok.add(upRateIndex.get(0)+1,SPEED); 
           tok.add(upRateIndex.get(0)+3,SPEED);
           String SpeedC=""; String m1="";
           for(String tokIng:tok) SpeedC= (SpeedC+tokIng+" ");
           
        int temp=subIp(tok.get(2),3);          
        int temp2=subIp(tok.get(2),2);          
//        if(temp>=0&&temp<=63) m1="interface vbui100";  
//        if(temp>=96&&temp<=111)m1="interface vbui200";
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        if(tok.get(2).substring(0,3).equals("196"))  m1="interface vbui1700" ;
        
        fs="   \n\n\n "+
         ("   config t\n")+ 
         ("    "+m1+"\n")+
         ("    "+SpeedC); 
     
     }
     
     if(SS.contains("ip-host")) { 
          
         String SSS;
         OTR.add(SS.replaceAll("[\\\r\\\n]+","").trim());
           if(!(SS.replaceAll("[\\\r\\\n]+","").trim().contains(OTR.get(0))))OTR=new ArrayList<>();
           SSS=OTR.get(0);
            finalSpeedC = new ArrayList<>();
           
            String [] tokin=SSS.split("\\s+");
            ArrayList<String> tok=new ArrayList<>();
            for(String h:tokin)tok.add(h);            
            tok.removeAll(Arrays.asList("", null));
            String SPEED=speedN(speed,tok.get(3));
           
            ArrayList<Integer> upRateIndex= searchIndex(tok,"author-temp-name");
            
           tok.remove(upRateIndex.get(0)+1); 
          // tok.remove(upRateIndex.get(0)+2); 
           tok.add(upRateIndex.get(0)+1,SPEED); 
           //tok.add(upRateIndex.get(0)+3,SPEED);
           String SpeedC=""; String m1="";
           for(String tokIng:tok) SpeedC= (SpeedC+tokIng+" ");
           
        int temp=subIp(tok.get(3),3);          
        int temp2=subIp(tok.get(3),2);          
//        if(temp>=0&&temp<=63) m1="interface vbui100";  
//        if(temp>=96&&temp<=111)m1="interface vbui200";
        if((temp>=0&&temp<=63)&&(temp2!=85)) m1="interface vbui100"; 
        if((temp>=96&&temp<=111)&&(temp2!=85)&&(temp2!=151))m1="interface vbui200";
        if((temp>=128&&temp<=254)&&(temp2==85))m1="interface vbui101";
        if((temp>=96&&temp<=127)&&(temp2==151))m1="interface vbui300";
        if(tok.get(3).substring(0,3).equals("196"))  m1="interface vbui1700" ;


      fs=("     config t")+ 
         ("\n   vbui-configuration")+
         ("\n   "+m1)+
         ("\n "+SpeedC); 
     
     }
     if(SS.contains("internal-vlan ")&&SS.contains("external-vlan ")){
          ArrayList<String> Data=new ArrayList<>();
         ArrayList<String> SO=new ArrayList<>();
         ArrayList<String> so=new ArrayList<>();
         Data=ppParamFind(SS);

        SO=pConfig(Data.get(4),speed,Data.get(1),Data.get(2),Data.get(3),"","",Data.get(0),"","","");
           so.add("   config t"); 
           so.add("    interface "+Data.get(0));
         
         for (int i = 0; i < newLiner(SS,"rate-limit ","!").size(); i++) so.add("   no "+newLiner(SS,"rate-limit ","!").get(i));             
         for (int i = 0; i < newLiner(SO.get(0),"rate-limit ","!").size(); i++) so.add(" "+newLiner(SO.get(0),"rate-limit ","!").get(i)); 
         so.add(""); so.add(""); so.add("");         
         fs=arrayList2Str(so);
         
     }
     
     if(SS.contains("internal-vlanid")&&SS.contains("external-vlanid")){
         ArrayList<String> Data=new ArrayList<>();
         ArrayList<String> SO=new ArrayList<>();
         ArrayList<String> so=new ArrayList<>();
         Data=vpnParamFind(SS);
       
         
         
     // no vrrp
        if(Data.get(7).trim().equals(""))so.add("                  ------  "+getIpAlloc(Data.get(3))+"  -  ER  -----  \n");
     // with vrrp   
        else so.add("                  ------  "+getIpAlloc(Data.get(3))+"  -  ER - A  -----  \n");
         SO=vpnConfig(Data.get(1),speed,Data.get(4),Data.get(5),Data.get(3),"","",Data.get(2),Data.get(7),"");
           so.add("   config t");
           so.add("     qos");
           so.add("      interface "+Data.get(0));
                     
         for (int i = 0; i < newLiner(SO.get(0),"rate-limit ","$").size(); i++) so.add(" "+newLiner(SO.get(0),"rate-limit ","$").get(i)); 
         so.add("   $"); so.add(""); so.add("");
        if(!(Data.get(7).trim().equals(""))){
            so.add("                  ------  "+getIpAlloc(Data.get(3))+"  -  ER - B  -----  ") ;        
            so.add(""); so.add(""); 
            so.add("   config t"); 
            so.add("     qos");
            so.add("      interface "+Data.get(0));            
         for (int i = 0; i < newLiner(SO.get(0),"rate-limit ","$").size(); i++) so.add(" "+newLiner(SO.get(0),"rate-limit ","$").get(i)); 
         so.add("   $"); 
         so.add(""); 
         so.add("");
        }
        fs=arrayList2Str(so);
     }
//       return finalSpeedC;
       return fs;
      }  
 //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
public ArrayList<String> takenIpFinder(ArrayList<String> data,String range){
     
         String sIp,eIp,str;int rIndex= range.indexOf("-");       
         sIp=range.substring(0,rIndex);
         eIp=range.substring(rIndex+1,range.length());    
      
  ArrayList<Integer> di=indexOfSymbol(sIp,".");
  int a=di.get(0); int b=di.get(1); int c=di.get(2);  
   ArrayList<Integer> ei=indexOfSymbol(eIp,".");
  int d=ei.get(0); int e=ei.get(1); int f=ei.get(2);
  
String  sIpNum=""+subIp(sIp,1)+subIp(sIp,2)+subIp(sIp,3)+subIp(sIp,4); //start IP w/o .
String  eIpNum=""+subIp(eIp,1)+subIp(eIp,2)+subIp(eIp,3)+subIp(eIp,4);
 int inc=-1,sub=-1,R1=0;
 String searchStr="",searchStr1="fbsdhfbsd44t4t4###",strTokIndex="",strTokIndex1="";
 
 ArrayList<String> bData=new ArrayList<>();

 
  if (((    subIp(sIp,3)>=0)&&(subIp(eIp,3)<64)) ||  // vbui100
        (subIp(sIp,1))==196  ||  //vbui1700
        ((95<(subIp(sIp,3))) && ((subIp(eIp,3))<=111)) || // vbui200
        (((128<=(subIp(sIp,3))) && ((subIp(eIp,3))<=255)&&((subIp(sIp,2))==85)))||
        (((96<=(subIp(sIp,3))) && ((subIp(eIp,3))<=127)&&((subIp(sIp,2))==151)))){
  //if((((subIp(sIp,3)>=0)&&(subIp(eIp,3)<64)) || (subIp(sIp,1))==196  || ((95<(subIp(sIp,3))) && ((subIp(eIp,3))<=111)))){
searchStr="ip-host description ";
searchStr1="ip host "+ subIp(sIp,1)+"."+subIp(sIp,2);
sub=32;
   
 } 
 
 

  if((63<(subIp(sIp,3)))&&((subIp(eIp,3))<96)&&(subIp(sIp,1))==10&&(subIp(eIp,1))==10) {

 searchStr="ip address "+subIp(sIp,1)+"."+subIp(sIp,2);
 strTokIndex1="ip address "; 
 
 sub=30;

     
 }
 
//   if((193<(subIp(sIp,3))) && ((subIp(sIp,3))<256)) {
   if((191<(subIp(sIp,3))) && ((subIp(sIp,3))<256)&&(subIp(sIp,1))==10&&(subIp(eIp,1))==10) {
 
 searchStr="ip address "+subIp(sIp,1)+"."+subIp(sIp,2);
 strTokIndex1="ip address "; 
 
 sub=29;

       
  }
 for (int v=0;v<data.size();v++){  
      if(data.get(v).contains(searchStr)||data.get(v).contains(searchStr1)) 
       bData.add(data.get(v)+" "); 
    
    }

ArrayList<String> X=new ArrayList<>();	

for (String strIp:bData){

if(strIp.contains("ip-host description ")) 
    strTokIndex=midString(strIp,"ip-host description "," ")+" ";
else if(strIp.contains("ip host"))
{
    strTokIndex="ip host ";
       
}
else 
    strTokIndex=strTokIndex1;


 str=networkIp(midString(strIp,strTokIndex," "),sub);

     
if(((ipNum(str,3).compareTo(ipNum(sIp,3)))>=0) && ((ipNum(eIp,3).compareTo(ipNum(str,3)))>=0)){
        X.add(str); 
   
        } 
// }
}
X.removeAll(Arrays.asList("", null));

 
    return X;
     
  }
 
public ArrayList<String> allIpFinder(String range){
       
         String sIp,eIp,str;int rIndex= range.indexOf("-");       
         sIp=range.substring(0,rIndex);
         eIp=range.substring(rIndex+1,range.length());    
      
  ArrayList<Integer> di=indexOfSymbol(sIp,".");
  int a=di.get(0); int b=di.get(1); int c=di.get(2);  // 10.130.48.1 // a=2,b=6,c=9 index 0f .
   ArrayList<Integer> ei=indexOfSymbol(eIp,".");
  int d=ei.get(0); int e=ei.get(1); int f=ei.get(2); //d=10,e=63,f=254
  

 int inc=-1,R1=0;
 
if(((    subIp(sIp,3)>=0)&&(subIp(eIp,3)<64)) ||  // vbui100
        (subIp(sIp,1))==196  ||  //vbui1700
        ((95<(subIp(sIp,3))) && ((subIp(eIp,3))<=111)) || // vbui200
        (((128<=(subIp(sIp,3))) && ((subIp(eIp,3))<=255)&&((subIp(sIp,2))==85)))||
        (((96<=(subIp(sIp,3))) && ((subIp(eIp,3))<=127)&&((subIp(sIp,2))==151)))){ //vbui300) {  //vbui101


 inc=1;

 R1=746;
     
}
 
 
  if((63<(subIp(sIp,3)))&&((subIp(eIp,3))<96)&&(subIp(sIp,1))==10&&(subIp(eIp,1))==10) {


 inc=5;

 R1=748;
     
  }

  if((191<(subIp(sIp,3))) && ((subIp(sIp,3))<256)&&(subIp(sIp,1))==10&&(subIp(eIp,1))==10) {
inc=9;
R1=752;

}
// 10.130.48.1 - 10.130.63.254  a=2 b=6 c=9   d=2 e =6 f =9
ArrayList<String> ALLIP= new ArrayList<>();   
   int R,x,xx,yy;
   String x1="",mm;   
    R=subIp(eIp,3) +1-subIp(sIp,3);   // 63+1-48 =16
    x=subIp(sIp,3); // 48 
    xx=str2num(""+subIp(sIp,1)+subIp(sIp,2));  //10130
    yy=(""+subIp(sIp,3)).length(); // 48 .length =2
   for (int j=1;j<=R;j++) // 1: 16
   {
	 
         for (int i=1;i<=254;i=i+inc )  {
		          if(inc>1) i=i-1;
                 			
           x1=""+xx+x+num2str(i);            // 10130481
           
           int cc=str2num(num2str(c+( (""+x).length() - yy)));         //    9+2-2=9 ,8 
//             System.out.println("x1="+x1+" ,cc="+cc+" ,x="+x+" ,R1="+R1+"sum=>"+(str2num(x1.substring(b-1,x1.length()))+R1));
          mm= x1.substring(0,a)+"."+ x1.substring(a,b-1) +"."+x1.substring(b-1,cc-2) +"."+x1.substring(cc-2,(""+x1).length()); 
          ALLIP.add(mm);
         }      
       x=((str2num(x1.substring(b-1,x1.length()))+R1)/1000);    //1

   }
     
  
    return ALLIP;
     
  }
 //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
public ArrayList freeIpFinder(int row,int col){
     
 return freeIpData[row][col];
}
  
//@@@@@@@@@@@@@@@@@@
public void dataUpdater(ArrayList<String> updatedData1){
     
      ArrayList<String>[][] temp= new ArrayList[IPrange.length][IPrange[0].length];
//      ArrayList<String>[] temp2= new ArrayList[dataColls(A,0).length];
//      ArrayList<String>[] temp3= new ArrayList[dataColls(A,0).length];
      
       for (int i = 0; i < IPrange.length; i++) {
         for (int j = 0; j <IPrange[i].length-1 ; j++) {
            temp[i][j]=new ArrayList<>();
          
         }
         
     }
//       for (int h = 0; h < temp2.length; h++) {
//           temp2[h]= new ArrayList<>();
//        
//    }
       
 //for(String mike:xmlListRead("updatedTask"))updatedData1.add(mike); 
  // IP Updater    
     for (int i = 1; i < IPrange.length; i++) {
         for (int j = 1; j <IPrange[i].length-1 ; j++) {
          temp[i][j]=takenIpFinder(updatedData1,IPrange[i][j]);
           
         }
     }
    
     for (int ii = 1; ii < IPrange.length; ii++) {
         for (int jj = 1; jj <IPrange[ii].length-1 ; jj++) {
          
             
             for (int k = 0; k < temp[ii][jj].size(); k++) {
                     takenIpData[ii][jj].add( temp[ii][jj].get(k) );
                 }
             
          }
     }
     
        for (int n = 1; n < IPrange.length; n++) {
         for (int p = 1; p <IPrange[n].length-1 ; p++) {
            freeIpData[n][p] =XOR ( freeIpData[n][p],takenIpData[n][p]); 
      
         }
         
     }
        
        // VLAN UPDATER
//        
//     
//           temp2= takenVlanFinder(updatedData1,temp2.length);
//        
//        
//      
//      for (int d = 1; d < temp2.length; d++) {
//          for (int i = 0; i < temp2[d].size(); i++) {
//              takenVlanData[i].add(temp2[d].get(i));  
//          }
//      }
//      
//       for (int w = 1; w < temp2.length; w++) {
//          freeVlanData[w]=XOR(freeVlanData[w],takenVlanData[w]);
//        
//      }  
//     
 }

       
//     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  
     //  final Clipboard clip=new Toolkit.getDefaultToolkit().getSystemClipboard();    
   
    public searchJFrame() throws FileNotFoundException {
     initComponents();
    
     //@@@@@@@@@@@@@@@@@
            helpData = new ArrayList<>();
       try{
           File fileData = new File("help.txt");
            Scanner in = new Scanner(fileData);
            while (in.hasNextLine())
            {helpData.add(in.nextLine());}
        }catch (Exception e){}
     //@@@@@@@@@@@@@@@@@
       if(!(xmlStringRead("CDATE").equals((""+cDate).trim()))){
        //backUp
        String path =(""+xmlStringRead("CDATE")+".txt");     
        copySrcDest("DATA.txt","backUpData/"+path);
        copySrcDest("updatedTask.xml","Recent/updatedTask.xml");
        copySrcDest("Report.xml","Recent/Report.xml");
        copySrcDest("BackUp.xml","Recent/BackUp.xml");
        //reset
        copySrcDest("reset/Report.xml","Report.xml");       
        copySrcDest("reset/updatedTask.xml","updatedTask.xml");
                
        xmlStringWrite(""+cDate,"CDATE");
       }
  
  // @@@@@@@@@@@@@@@@@@@@
  ArrayList <String> DATA =xmlListRead("FIND_HISTORY");
   if(!(DATA==null)) {
     for (int i = 0; i <DATA.size(); i++){
            searchItem.insertItemAt(DATA.get(i),i);
            if(i>24) break;
     }
   }
//@@@@@@@@@@@@@@@@@@@@
   stickyNote.setText(xmlStringRead("Sticky"));  
 //@@@@@@@@@@@@@@@@@@@ 
 if(new File ("data.xls").exists()){ //check the database file is existed or not
           A=xlRead("data.xls","database");
       oltTt=xlRead("data.xls","olt-tt");
     IPrange=xlRead("data.xls","ip-group-range");  
     ipAllocationData=xlRead("data.xls","ipAllocation");
     brData=xlRead("data.xls","brData");
     erData=xlRead("data.xls","erData");
     swData=xlRead("data.xls","swData");
      } else{
                  outputtext.append("\n\n\n\n\n                 <<<                                                 "
           + "                                              Browse  EXCEL  Database First ....    !!!!"
           + "                                                          >>>");  
                   }
         //@@@@@@@@@@@@@@@@@@@ 
 if(new File ("DATA.txt").exists()){
       try{
           File fileData = new File("DATA.txt");
            Scanner in = new Scanner(fileData);
            allData = new ArrayList<>();
            while (in.hasNextLine())
            {allData.add(in.nextLine());}
            allData.removeAll(Arrays.asList("", null));
         } 
        catch (FileNotFoundException ex) {
        Logger.getLogger(searchJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                ArrayList <Integer> indiBig= searchIndexor(allData,"ip-host","ip host");
               // ArrayList <Integer> indiBig_1= searchIndex(allData,"ip host");     // 24
                ArrayList <String>iphostData1 = new ArrayList<>();
            String b="";
            boolean c;
            String B;
          
               for(int p=((indiBig.size())-1);p>=0;p--)
           {
                B=allData.get((indiBig.get(p))+1);
               c=B.contains("ip-host")||B.contains("ip host");
               if (c==true) {
                   b="";
                   iphostData1.add(allData.get(indiBig.get(p))+b);
                   }
               if(c==false){
                   b=allData.get((indiBig.get(p))+1);
                   iphostData1.add(allData.get(indiBig.get(p))+b);
                  }
            }
               

             
          iphostData=new ArrayList<>();     
             ArrayList <Integer> indiBig2= searchIndexor(allData,"ip-host","ip host");
             // ArrayList <Integer> indiBig_2= searchIndex(allData,"ip host"); // 24
         
             for(int p=((indiBig2.size())-1);p>=0;p--){
                 if(!(allData.get((indiBig2.get(p))+1).contains("ip host")||allData.get((indiBig2.get(p))+1).contains("ip-host"))) 
                      allData.remove((indiBig2.get(p))+1);
             }
             
         
             ArrayList <String> newAllData= new ArrayList<>();
      
         for(String S:allData)
         if(!(S.contains("ip host")||S.contains("ip-host"))) newAllData.add(S);
         
//         for(String D:allData)                               // 24
//         if(!(D.contains("ip host"))) newAllData.add(D);
         
         iphostData1.removeAll(Arrays.asList("", null));
         newAllData.removeAll(Arrays.asList("", null));
               
         newAllData.forEach((V) -> {
             iphostData.add(V);
        });          
  
     for (int i = 0; i < iphostData1.size(); i++) {
           int m=iphostData1.size()-1;
           iphostData.add(iphostData1.get(m-i));
           
        }
iphostDataA=new ArrayList<>();  
updatedData= new ArrayList<>();


iphostDataA=new ArrayList(iphostData);     
  for(String mike:xmlListRead("updatedTask"))iphostDataA.add(mike); 
   for(String mike:xmlListRead("updatedTask"))updatedData.add(mike); 
  loadedData=iphostDataA;
  takenVlanData= new ArrayList[dataColls(A,0).length];
   
  allIpData=new  ArrayList[IPrange.length][IPrange[0].length];
  takenIpData=new  ArrayList[IPrange.length][IPrange[0].length];
  freeIpData= new  ArrayList[IPrange.length][IPrange[0].length];
  
    for (int kk = 0; kk < takenVlanData.length; kk++) {
         takenVlanData[kk]= new ArrayList<>();
        // freeVlanData[kk]= new ArrayList<>();
     }
     // FREE IP 
  
   for (int i = 0; i < IPrange.length; i++) {
         for (int j = 0; j <IPrange[i].length-1 ; j++) {
            allIpData[i][j]=new ArrayList<>();
            takenIpData[i][j]=new ArrayList<>();
            freeIpData[i][j]= new ArrayList<>();
         }
         
     }
  
     for (int i = 1; i < IPrange.length; i++) {
         for (int j = 1; j <IPrange[i].length-1 ; j++) {
            allIpData[i][j]=allIpFinder (IPrange[i][j]); 
            takenIpData[i][j]=takenIpFinder(loadedData,IPrange[i][j]);
           
         }
         
     }
     
      for (int i = 1; i < IPrange.length; i++) {
         for (int j = 1; j <IPrange[i].length-1 ; j++) {
            freeIpData[i][j] =XOR ( allIpData[i][j],takenIpData[i][j]); 
      
         }
         
     }
  
  //FREE VLAN
       allVlanData = new ArrayList<>();
            for (int j = 1001; j < 1601; j++) {
                allVlanData.add("" + j);
            }
//  
//     
//        
//         takenVlanData=takenVlanFinder(iphostDataA,takenVlanData.length);
//     
//     for (int z = 1; z < takenVlanData.length; z++) {
//         freeVlanData[z]=XOR(allVlanData,takenVlanData[z]);
//////     }
 
 }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        outputtext = new javax.swing.JTextArea();
        selector = new javax.swing.JTabbedPane();
        jpepon = new javax.swing.JPanel();
        eponSpeed = new javax.swing.JTextField();
        eponVlan = new javax.swing.JTextField();
        eponPort = new javax.swing.JTextField();
        eponMac = new javax.swing.JTextField();
        eponName = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        eponRun = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        mnRest = new javax.swing.JButton();
        msanResetPort = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        msanPort = new javax.swing.JTextField();
        msanVlan = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        eponRest = new javax.swing.JButton();
        jpinternet = new javax.swing.JPanel();
        internet = new javax.swing.JPanel();
        bbReset = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        bbWanIp = new javax.swing.JTextField();
        bbVlan = new javax.swing.JTextField();
        bbSpeed = new javax.swing.JTextField();
        bbAccess = new javax.swing.JTextField();
        bbName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        iImport = new javax.swing.JButton();
        bbRun = new javax.swing.JButton();
        jppublicEr = new javax.swing.JPanel();
        pRun = new javax.swing.JButton();
        pp = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pImport = new javax.swing.JButton();
        pWanIp = new javax.swing.JTextField();
        pLanIp = new javax.swing.JTextField();
        pSubnet = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        pReset = new javax.swing.JButton();
        pVlan = new javax.swing.JTextField();
        pSpeed = new javax.swing.JTextField();
        pName = new javax.swing.JTextField();
        pAccess = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jpvpn = new javax.swing.JPanel();
        vpn = new javax.swing.JPanel();
        vpnAccess = new javax.swing.JTextField();
        vpnSpeed = new javax.swing.JTextField();
        vpnVlan = new javax.swing.JTextField();
        vpnWanIp = new javax.swing.JTextField();
        vpnLanIp = new javax.swing.JTextField();
        vpnSubnet = new javax.swing.JTextField();
        vpnVrf = new javax.swing.JTextField();
        vpnVrrp = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        vpnName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        vpnRun = new javax.swing.JButton();
        vpnReset = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        vpnImnort = new javax.swing.JButton();
        jpolt = new javax.swing.JPanel();
        oltVlan = new javax.swing.JTextField();
        oltGemPort = new javax.swing.JTextField();
        oltSpeed = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        mduSpeed = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        mduVlan = new javax.swing.JTextField();
        mduPort = new javax.swing.JTextField();
        mduRun = new javax.swing.JButton();
        mduReset = new javax.swing.JButton();
        jLabel81 = new javax.swing.JLabel();
        oltRun = new javax.swing.JButton();
        oltReset = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        searchItem = new javax.swing.JComboBox<>();
        findp = new javax.swing.JButton();
        findall = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        findRemark = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        msag = new javax.swing.JTextField();
        jLMsag = new javax.swing.JLabel();
        jLMsagIp = new javax.swing.JLabel();
        msagIp = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        msagTag = new javax.swing.JLabel();
        area = new javax.swing.JLabel();
        vlanTag = new javax.swing.JLabel();
        clear = new javax.swing.JButton();
        vlan = new javax.swing.JTextField();
        jLVlan = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        replacevlan = new javax.swing.JButton();
        replacespeed = new javax.swing.JButton();
        block = new javax.swing.JButton();
        terminate = new javax.swing.JButton();
        resume1 = new javax.swing.JButton();
        changevlan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        save = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        rSearchRemark = new javax.swing.JLabel();
        rSrchRemark = new javax.swing.JLabel();
        rSearchOK = new javax.swing.JButton();
        rSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        stickyNote = new javax.swing.JTextArea();
        menu = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        database = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        backupBRER = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        dailyReport = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        overAllReport = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        resetOverAllReport = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        resetReport = new javax.swing.JMenuItem();
        Data = new javax.swing.JMenu();
        coper = new javax.swing.JCheckBoxMenuItem();
        fiber = new javax.swing.JCheckBoxMenuItem();
        export_Msag_Data = new javax.swing.JMenuItem();
        DsaveExcel = new javax.swing.JMenuItem();
        Dreset = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        copy = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        paste = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        cut = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        edit_delete = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        help = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TANA SMC TOOL                                                                                                                                                      </Tamiru Molla Hailu># Mike Zc$de #</tamirumolla24@gmail.com>");
        setBackground(new java.awt.Color(255, 255, 255));
        setExtendedState(1);
        setIconImages(null);
        setLocation(new java.awt.Point(30, 5));
        setMaximizedBounds(new java.awt.Rectangle(40, 0, 1190, 365345345));
        setMinimumSize(new java.awt.Dimension(20, 20));
        setSize(new java.awt.Dimension(1900, 720));

        outputtext.setBackground(new java.awt.Color(0, 0, 42));
        outputtext.setColumns(85);
        outputtext.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        outputtext.setForeground(new java.awt.Color(0, 255, 0));
        outputtext.setRows(4);
        outputtext.setCaretColor(new java.awt.Color(255, 255, 255));
        outputtext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                outputtextFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                outputtextFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(outputtext);

        selector.setBackground(new java.awt.Color(255, 255, 255));
        selector.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        selector.setToolTipText("msag");
        selector.setName(""); // NOI18N

        jpepon.setBackground(new java.awt.Color(0, 0, 51));
        jpepon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jpepon.setForeground(new java.awt.Color(255, 255, 255));
        jpepon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eponSpeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eponSpeed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                eponSpeedFocusGained(evt);
            }
        });
        eponSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eponSpeedActionPerformed(evt);
            }
        });
        jpepon.add(eponSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 40, 130, -1));

        eponVlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eponVlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                eponVlanFocusGained(evt);
            }
        });
        jpepon.add(eponVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 70, 130, -1));

        eponPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eponPort.setText("0/3/0 onu ");
        eponPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                eponPortFocusGained(evt);
            }
        });
        jpepon.add(eponPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 100, 130, -1));

        eponMac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eponMac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                eponMacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                eponMacFocusLost(evt);
            }
        });
        jpepon.add(eponMac, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 130, 130, -1));

        eponName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eponName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                eponNameFocusGained(evt);
            }
        });
        jpepon.add(eponName, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 10, 130, -1));

        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("        Speed");
        jpepon.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 80, -1));

        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("   Vlan");
        jpepon.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 60, -1));

        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("     Port");
        jpepon.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 60, -1));

        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText(" Customer Name");
        jpepon.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 10, 110, -1));

        eponRun.setText("RUN");
        eponRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eponRunActionPerformed(evt);
            }
        });
        jpepon.add(eponRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 58, 24));

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Mac");
        jpepon.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, -1, -1));

        jPanel5.setBackground(new java.awt.Color(0, 0, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mnRest.setText("Reset");
        mnRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRestActionPerformed(evt);
            }
        });
        jPanel5.add(mnRest, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 155, -1, 24));

        msanResetPort.setText("Reset / (+)service- port");
        msanResetPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msanResetPortActionPerformed(evt);
            }
        });
        jPanel5.add(msanResetPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 155, -1, 24));

        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("    Port");
        jPanel5.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 70, 50, -1));

        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("   Vlan");
        jPanel5.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 42, 40, -1));

        msanPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        msanPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                msanPortFocusGained(evt);
            }
        });
        msanPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msanPortActionPerformed(evt);
            }
        });
        jPanel5.add(msanPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 130, -1));

        msanVlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        msanVlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                msanVlanFocusGained(evt);
            }
        });
        jPanel5.add(msanVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 130, -1));

        jpepon.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 330, 195));

        jPanel6.setBackground(new java.awt.Color(0, 0, 51));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eponRest.setText("Reset");
        eponRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eponRestActionPerformed(evt);
            }
        });
        jPanel6.add(eponRest, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, -1, 24));

        jpepon.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 265, 195));

        selector.addTab("      MSAG / MSAN    ", jpepon);

        jpinternet.setBackground(new java.awt.Color(0, 0, 51));
        jpinternet.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jpinternet.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        internet.setBackground(new java.awt.Color(0, 0, 51));
        internet.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        internet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                internetMouseClicked(evt);
            }
        });
        internet.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bbReset.setText("Reset");
        bbReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbResetActionPerformed(evt);
            }
        });
        internet.add(bbReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, -1, 24));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText(" Customer Name");
        internet.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 110, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("     Access No.");
        internet.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 90, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("    Speed");
        internet.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 60, -1));

        bbWanIp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bbWanIp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bbWanIpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                bbWanIpFocusLost(evt);
            }
        });
        internet.add(bbWanIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 130, -1));

        bbVlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bbVlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bbVlanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                bbVlanFocusLost(evt);
            }
        });
        internet.add(bbVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 130, -1));

        bbSpeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bbSpeed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bbSpeedFocusGained(evt);
            }
        });
        internet.add(bbSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 130, -1));

        bbAccess.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bbAccess.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bbAccessFocusGained(evt);
            }
        });
        internet.add(bbAccess, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 130, -1));

        bbName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bbName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bbNameFocusGained(evt);
            }
        });
        internet.add(bbName, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 130, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText(" Vlan ");
        jButton1.setActionCommand("  Vlan  ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        internet.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 103, 100, 20));
        jButton1.getAccessibleContext().setAccessibleName("Vlan");

        jButton2.setBackground(new java.awt.Color(0, 0, 51));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Wan  IP");
        jButton2.setToolTipText("");
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        internet.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 133, 100, 20));

        iImport.setText("==> IMPORT");
        iImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iImportActionPerformed(evt);
            }
        });
        internet.add(iImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 110, 24));
        iImport.getAccessibleContext().setAccessibleName("IMPORT");

        bbRun.setText("Run");
        bbRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbRunActionPerformed(evt);
            }
        });
        internet.add(bbRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, -1, 24));

        jpinternet.add(internet, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 650, 195));

        selector.addTab("     BB-Internet     ", jpinternet);

        jppublicEr.setBackground(new java.awt.Color(0, 0, 51));
        jppublicEr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jppublicEr.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pRun.setText("Run");
        pRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pRunActionPerformed(evt);
            }
        });
        jppublicEr.add(pRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, 24));

        pp.setBackground(new java.awt.Color(0, 0, 51));
        pp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        pp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ppMouseClicked(evt);
            }
        });
        pp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setBackground(new java.awt.Color(0, 0, 51));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Vlan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        pp.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 103, 70, 20));

        jButton3.setBackground(new java.awt.Color(0, 0, 51));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Network IP");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pp.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 14, 130, 20));

        pImport.setText("==> IMPORT");
        pImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pImportActionPerformed(evt);
            }
        });
        pp.add(pImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 154, 110, 24));

        pWanIp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pWanIp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pWanIpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pWanIpFocusLost(evt);
            }
        });
        pp.add(pWanIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 130, -1));

        pLanIp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pLanIp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pLanIpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pLanIpFocusLost(evt);
            }
        });
        pp.add(pLanIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 130, -1));

        pSubnet.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pSubnet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pSubnetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pSubnetFocusLost(evt);
            }
        });
        pp.add(pSubnet, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 130, -1));

        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("  Subnet Mask");
        pp.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 90, -1));

        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("     Public  IP");
        pp.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 80, -1));

        pReset.setText("Reset");
        pReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pResetActionPerformed(evt);
            }
        });
        pp.add(pReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, -1, 24));

        pVlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pVlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pVlanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pVlanFocusLost(evt);
            }
        });
        pp.add(pVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 130, -1));

        pSpeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pSpeed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pSpeedFocusGained(evt);
            }
        });
        pp.add(pSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 130, -1));

        pName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pNameFocusGained(evt);
            }
        });
        pp.add(pName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 130, -1));

        pAccess.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pAccess.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pAccessFocusGained(evt);
            }
        });
        pAccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pAccessActionPerformed(evt);
            }
        });
        pp.add(pAccess, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 130, -1));

        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("     Access No.");
        pp.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, -1));

        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("        Speed");
        pp.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 70, -1));

        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText(" Customer Name");
        pp.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, -1));

        jppublicEr.add(pp, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 660, 195));

        selector.addTab("       Public       ", jppublicEr);

        jpvpn.setBackground(new java.awt.Color(0, 0, 51));
        jpvpn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jpvpn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vpn.setBackground(new java.awt.Color(0, 0, 51));
        vpn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        vpn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vpnMouseClicked(evt);
            }
        });
        vpn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vpnAccess.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnAccess.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnAccessFocusGained(evt);
            }
        });
        vpnAccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vpnAccessActionPerformed(evt);
            }
        });
        vpn.add(vpnAccess, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 130, -1));

        vpnSpeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnSpeed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnSpeedFocusGained(evt);
            }
        });
        vpn.add(vpnSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 130, -1));

        vpnVlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnVlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnVlanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vpnVlanFocusLost(evt);
            }
        });
        vpn.add(vpnVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 130, -1));

        vpnWanIp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnWanIp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnWanIpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vpnWanIpFocusLost(evt);
            }
        });
        vpn.add(vpnWanIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 130, -1));

        vpnLanIp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnLanIp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnLanIpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vpnLanIpFocusLost(evt);
            }
        });
        vpn.add(vpnLanIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 130, -1));

        vpnSubnet.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnSubnet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnSubnetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vpnSubnetFocusLost(evt);
            }
        });
        vpn.add(vpnSubnet, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 130, -1));

        vpnVrf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnVrf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnVrfFocusGained(evt);
            }
        });
        vpn.add(vpnVrf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 130, -1));

        vpnVrrp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnVrrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnVrrpFocusGained(evt);
            }
        });
        vpn.add(vpnVrrp, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 130, -1));

        jButton5.setBackground(new java.awt.Color(0, 0, 51));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Network IP");
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        vpn.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 132, 120, 22));

        jButton6.setBackground(new java.awt.Color(0, 0, 51));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Vlan");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        vpn.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 102, 120, 20));

        vpnName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vpnName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vpnNameFocusGained(evt);
            }
        });
        vpn.add(vpnName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 130, -1));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("     Access No.");
        vpn.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 90, -1));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("    Speed");
        vpn.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 60, -1));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("    Customer Name");
        vpn.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 120, -1));

        vpnRun.setText("Run");
        vpnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vpnRunActionPerformed(evt);
            }
        });
        vpn.add(vpnRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 70, 24));

        vpnReset.setText("Reset");
        vpnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vpnResetActionPerformed(evt);
            }
        });
        vpn.add(vpnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, -1, 24));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("       Lan IP");
        vpn.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 70, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("     Subnet Mask");
        vpn.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 100, -1));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("          VRF");
        vpn.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 70, -1));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("       Vrrp");
        vpn.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 60, -1));

        vpnImnort.setText("==> IMPORT");
        vpnImnort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vpnImnortActionPerformed(evt);
            }
        });
        vpn.add(vpnImnort, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 110, 24));

        jpvpn.add(vpn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 650, 195));

        selector.addTab("        VPN         ", jpvpn);

        jpolt.setBackground(new java.awt.Color(0, 0, 51));
        jpolt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jpolt.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        oltVlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        oltVlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                oltVlanFocusGained(evt);
            }
        });
        oltVlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oltVlanActionPerformed(evt);
            }
        });
        jpolt.add(oltVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 130, -1));

        oltGemPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        oltGemPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                oltGemPortFocusGained(evt);
            }
        });
        jpolt.add(oltGemPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 130, -1));

        oltSpeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        oltSpeed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                oltSpeedFocusGained(evt);
            }
        });
        jpolt.add(oltSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 130, -1));

        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("        Vlan");
        jpolt.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 42, 70, -1));

        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("  Gem Port");
        jpolt.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, -1));

        mduSpeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mduSpeed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mduSpeedFocusGained(evt);
            }
        });
        jpolt.add(mduSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 130, -1));

        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("    Speed-Index");
        jpolt.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 100, -1));

        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("        Vlan");
        jpolt.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 70, -1));

        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("  Port");
        jpolt.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 50, -1));

        mduVlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mduVlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mduVlanFocusGained(evt);
            }
        });
        mduVlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mduVlanActionPerformed(evt);
            }
        });
        jpolt.add(mduVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 130, -1));

        mduPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mduPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mduPortFocusGained(evt);
            }
        });
        jpolt.add(mduPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 130, -1));

        mduRun.setText("Run");
        mduRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mduRunActionPerformed(evt);
            }
        });
        jpolt.add(mduRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 60, 24));

        mduReset.setText("Reset");
        mduReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mduResetActionPerformed(evt);
            }
        });
        jpolt.add(mduReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, -1, 24));

        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("         Speed");
        jpolt.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 12, 80, -1));

        oltRun.setText("Run");
        oltRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oltRunActionPerformed(evt);
            }
        });
        jpolt.add(oltRun, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 60, 24));

        oltReset.setText("Reset");
        oltReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oltResetActionPerformed(evt);
            }
        });
        jpolt.add(oltReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, -1, 24));

        jLabel15.setForeground(new java.awt.Color(153, 255, 0));
        jLabel15.setText(" < OLT >");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jLabel15.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jpolt.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 5, 280, 198));

        jLabel16.setForeground(new java.awt.Color(153, 255, 0));
        jLabel16.setText(" < MDU >");
        jLabel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jLabel16.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jpolt.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 5, 280, 198));

        selector.addTab("       OLT / MDU      ", jpolt);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchItem.setEditable(true);
        searchItem.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        searchItem.setMaximumRowCount(40);
        searchItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "mike zcode" }));
        searchItem.setToolTipText("");
        searchItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchItemFocusGained(evt);
            }
        });
        searchItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchItemActionPerformed(evt);
            }
        });
        jPanel1.add(searchItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 10, 326, 24));

        findp.setBackground(new java.awt.Color(0, 0, 102));
        findp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        findp.setForeground(new java.awt.Color(255, 255, 255));
        findp.setText("Find ");
        findp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        findp.setContentAreaFilled(false);
        findp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findpActionPerformed(evt);
            }
        });
        jPanel1.add(findp, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 10, 70, 24));

        findall.setBackground(new java.awt.Color(0, 0, 102));
        findall.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        findall.setForeground(new java.awt.Color(255, 255, 255));
        findall.setText("Find All");
        findall.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        findall.setContentAreaFilled(false);
        findall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findallActionPerformed(evt);
            }
        });
        jPanel1.add(findall, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 90, 24));

        reset.setBackground(new java.awt.Color(0, 0, 102));
        reset.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reset.setForeground(new java.awt.Color(255, 255, 255));
        reset.setText("Reset");
        reset.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        reset.setContentAreaFilled(false);
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel1.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(519, 10, 75, 24));

        findRemark.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        findRemark.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(findRemark, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 36, 325, 15));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 123, 600, 55));

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        msag.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        msag.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        msag.setAlignmentY(3.0F);
        msag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                msagFocusGained(evt);
            }
        });
        msag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msagActionPerformed(evt);
            }
        });
        jPanel2.add(msag, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 114, 24));
        msag.getAccessibleContext().setAccessibleName("");

        jLMsag.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLMsag.setForeground(new java.awt.Color(255, 255, 255));
        jLMsag.setText("MSAG");
        jPanel2.add(jLMsag, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 42, 15));

        jLMsagIp.setBackground(new java.awt.Color(153, 255, 102));
        jLMsagIp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLMsagIp.setForeground(new java.awt.Color(255, 255, 255));
        jLMsagIp.setText("MSAG IP");
        jPanel2.add(jLMsagIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 62, 15));

        msagIp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        msagIp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        msagIp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                msagIpFocusGained(evt);
            }
        });
        jPanel2.add(msagIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 490, 24));

        search.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        search.setText("Search");
        search.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        jPanel2.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 70, 24));

        msagTag.setBackground(new java.awt.Color(153, 255, 102));
        msagTag.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        msagTag.setForeground(new java.awt.Color(255, 255, 255));
        msagTag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(msagTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 193, 15));

        area.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        area.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(area, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 110, 15));

        vlanTag.setBackground(new java.awt.Color(153, 255, 102));
        vlanTag.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        vlanTag.setForeground(new java.awt.Color(255, 255, 255));
        vlanTag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(vlanTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 50, 15));

        clear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clear.setText("Reset");
        clear.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        jPanel2.add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 70, 24));

        vlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        vlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vlanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vlanFocusLost(evt);
            }
        });
        jPanel2.add(vlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 114, 24));

        jLVlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLVlan.setForeground(new java.awt.Color(255, 255, 255));
        jLVlan.setText(" VLAN");
        jLVlan.setToolTipText("");
        jPanel2.add(jLVlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 43, 15));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 120));

        jPanel3.setBackground(new java.awt.Color(0, 0, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        replacevlan.setBackground(new java.awt.Color(0, 0, 102));
        replacevlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        replacevlan.setForeground(new java.awt.Color(255, 255, 255));
        replacevlan.setText("Change Vlan");
        replacevlan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        replacevlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replacevlanActionPerformed(evt);
            }
        });
        jPanel3.add(replacevlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 15, 110, 24));

        replacespeed.setBackground(new java.awt.Color(0, 0, 102));
        replacespeed.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        replacespeed.setForeground(new java.awt.Color(255, 255, 255));
        replacespeed.setText("Change Speed");
        replacespeed.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        replacespeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replacespeedActionPerformed(evt);
            }
        });
        jPanel3.add(replacespeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 120, 24));

        block.setBackground(new java.awt.Color(0, 0, 102));
        block.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        block.setForeground(new java.awt.Color(255, 255, 255));
        block.setText("TW-Block");
        block.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockActionPerformed(evt);
            }
        });
        jPanel3.add(block, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 15, 77, 24));

        terminate.setBackground(new java.awt.Color(0, 0, 102));
        terminate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        terminate.setForeground(new java.awt.Color(255, 255, 255));
        terminate.setText("Terminate");
        terminate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        terminate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminateActionPerformed(evt);
            }
        });
        jPanel3.add(terminate, new org.netbeans.lib.awtextra.AbsoluteConstraints(504, 15, 87, 24));

        resume1.setBackground(new java.awt.Color(0, 0, 102));
        resume1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        resume1.setForeground(new java.awt.Color(255, 255, 255));
        resume1.setText("Resume");
        resume1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        resume1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resume1ActionPerformed(evt);
            }
        });
        jPanel3.add(resume1, new org.netbeans.lib.awtextra.AbsoluteConstraints(427, 15, 70, 24));

        changevlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        changevlan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        changevlan.setMinimumSize(new java.awt.Dimension(6, 13));
        changevlan.setPreferredSize(new java.awt.Dimension(6, 13));
        changevlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                changevlanFocusGained(evt);
            }
        });
        changevlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changevlanActionPerformed(evt);
            }
        });
        jPanel3.add(changevlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 15, 70, 24));

        jLabel17.setText("jLabel17");
        jLabel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 5, 325, 44));
        jLabel17.getAccessibleContext().setAccessibleName("");

        jLabel19.setText("jLabel17");
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 5, 261, 44));

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 600, 55));

        jToolBar1.setBackground(new java.awt.Color(0, 0, 51));
        jToolBar1.setForeground(new java.awt.Color(0, 0, 51));
        jToolBar1.setRollover(true);
        jToolBar1.setToolTipText("");
        jToolBar1.setMaximumSize(new java.awt.Dimension(980, 2147483647));

        save.setBackground(new java.awt.Color(255, 255, 255));
        save.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        save.setForeground(new java.awt.Color(51, 51, 51));
        save.setText("    save    ");
        save.setEnabled(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jToolBar1.add(save);

        jLabel3.setBackground(new java.awt.Color(0, 0, 42));
        jLabel3.setForeground(new java.awt.Color(0, 0, 42));
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 14));
        jToolBar1.add(jLabel3);
        jToolBar1.add(jLabel14);

        rSearchRemark.setBackground(new java.awt.Color(0, 0, 51));
        rSearchRemark.setForeground(new java.awt.Color(255, 0, 51));
        rSearchRemark.setPreferredSize(new java.awt.Dimension(800, 14));
        jToolBar1.add(rSearchRemark);

        rSrchRemark.setForeground(new java.awt.Color(255, 255, 255));
        rSrchRemark.setPreferredSize(new java.awt.Dimension(34, 23));
        jToolBar1.add(rSrchRemark);

        rSearchOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/searchMsag/ESearch.PNG"))); // NOI18N
        rSearchOK.setFocusable(false);
        rSearchOK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rSearchOK.setPreferredSize(new java.awt.Dimension(47, 26));
        rSearchOK.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rSearchOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSearchOKActionPerformed(evt);
            }
        });
        jToolBar1.add(rSearchOK);

        rSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 51)));
        rSearch.setPreferredSize(new java.awt.Dimension(300, 23));
        rSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rSearchFocusLost(evt);
            }
        });
        jToolBar1.add(rSearch);

        jScrollPane1.setBackground(new java.awt.Color(153, 255, 0));
        jScrollPane1.setToolTipText("Note");

        stickyNote.setBackground(new java.awt.Color(255, 255, 153));
        stickyNote.setColumns(2);
        stickyNote.setRows(2);
        stickyNote.setTabSize(2);
        stickyNote.setText("Note");
        stickyNote.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stickyNoteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stickyNoteFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(stickyNote);

        menu.setBackground(new java.awt.Color(204, 204, 204));
        menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        menu.setForeground(new java.awt.Color(0, 0, 51));
        menu.setFocusable(false);
        menu.setMaximumSize(new java.awt.Dimension(165, 1173));
        menu.setOpaque(false);
        menu.setPreferredSize(new java.awt.Dimension(100, 24));
        menu.setSelectionModel(null);
        menu.setVerifyInputWhenFocusTarget(false);

        jMenu5.setBackground(new java.awt.Color(0, 0, 51));
        jMenu5.setText("    File    ");
        jMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu5ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        database.setText("      Import  Excel Database                     ");
        database.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseActionPerformed(evt);
            }
        });
        jMenu5.add(database);
        jMenu5.add(jMenuItem2);

        backupBRER.setText("      Import Back up (BR-ER)");
        backupBRER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backupBRERActionPerformed(evt);
            }
        });
        jMenu5.add(backupBRER);
        jMenu5.add(jMenuItem3);

        dailyReport.setText("      Save as  (Daily Report)");
        dailyReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailyReportActionPerformed(evt);
            }
        });
        jMenu5.add(dailyReport);
        jMenu5.add(jMenuItem4);

        overAllReport.setText("      Save as (Over All Report)");
        overAllReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overAllReportActionPerformed(evt);
            }
        });
        jMenu5.add(overAllReport);
        jMenu5.add(jMenuItem5);

        resetOverAllReport.setText("      Reset  ( Over All Report )");
        resetOverAllReport.setEnabled(false);
        resetOverAllReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetOverAllReportActionPerformed(evt);
            }
        });
        jMenu5.add(resetOverAllReport);
        jMenu5.add(jMenuItem6);

        resetReport.setText("      Reset  ( Daily Report )");
        resetReport.setEnabled(false);
        resetReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetReportActionPerformed(evt);
            }
        });
        jMenu5.add(resetReport);

        menu.add(jMenu5);

        Data.setText("Data");

        coper.setSelected(true);
        coper.setText("COPER");
        Data.add(coper);

        fiber.setSelected(true);
        fiber.setText("FIBER");
        Data.add(fiber);

        export_Msag_Data.setText("Export_Msag_Data");
        export_Msag_Data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export_Msag_DataActionPerformed(evt);
            }
        });
        Data.add(export_Msag_Data);

        DsaveExcel.setText("Save_As_Excel");
        DsaveExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DsaveExcelActionPerformed(evt);
            }
        });
        Data.add(DsaveExcel);

        Dreset.setText("Reset");
        Dreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DresetActionPerformed(evt);
            }
        });
        Data.add(Dreset);

        menu.add(Data);

        edit.setText("    Edit    ");
        edit.add(jMenuItem14);

        copy.setText("  Copy                                                                                                              ");
        copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyActionPerformed(evt);
            }
        });
        edit.add(copy);
        edit.add(jMenuItem12);

        paste.setText("  Paste                                                           ");
        paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteActionPerformed(evt);
            }
        });
        edit.add(paste);
        edit.add(jMenuItem13);

        cut.setText("  Cut                                                           ");
        cut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutActionPerformed(evt);
            }
        });
        edit.add(cut);
        edit.add(jMenuItem15);

        edit_delete.setText("  Delete");
        edit_delete.setActionCommand(" Delete");
        edit_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_deleteActionPerformed(evt);
            }
        });
        edit.add(edit_delete);

        menu.add(edit);

        jMenu1.setText("    Help   ");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem17);

        help.setText("      Help ( Tips for  Commands and  Configuration)                                             ");
        help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed(evt);
            }
        });
        jMenu1.add(help);
        jMenu1.add(jMenuItem16);

        menu.add(jMenu1);

        setJMenuBar(menu);
        menu.getAccessibleContext().setAccessibleName("");
        menu.getAccessibleContext().setAccessibleDescription("");
        menu.getAccessibleContext().setAccessibleParent(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selector, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selector, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
  if((!(outputtext.getSelectedText()==null))){
  if(outputtext.getSelectedText().trim().length()<=4){
      msag.setText("");
      vlan.setText(outputtext.getSelectedText().trim());
  }
  }     
  
  if(!(msag.getText().isEmpty() && vlan.getText().isEmpty())) {
        
        int index=-1;
        String AA=" ";                  
        ArrayList<Integer> sInd= new ArrayList<>();

if(vlan.getText().equals(""))sInd=searchIndex(dataColls(A,0),String.valueOf(msag.getText()).toLowerCase());
if(msag.getText().equals(""))sInd=searchIndex(dataColls(A,2),String.valueOf(vlan.getText()).toLowerCase());
if(sInd.size()==1)count=0;
         searchIndex=sInd.get(count);
         index=searchIndex;         
         String temp =String.valueOf(A[index][10]);         
         String temp2="MSAN";
         String temp3="MDU";
         
 if (temp.contains(temp2)|| temp.contains(temp3)) {
 msagIp.setText("telnet "+String.valueOf(A[index][1])+" vrf OAM"); 
 }  else{  
 msagIp.setText("telnet "+String.valueOf(A[index][1])+" 1123 vrf OAM"); 
 }
 vlanTag.setText(String.valueOf(A[index][2]));
 msagTag.setText(String.valueOf(A[index][0]));msagTag.setHorizontalAlignment(JLabel.CENTER);
 area.setText(String.valueOf(A[index][3]));area.setHorizontalAlignment(JLabel.CENTER);
 outputtext.setText("");
 outputtext.append("\n\n\n"+"                            Qinq interface for vbui100    :     "+qinqvbui100(index));
 outputtext.append("\n\n"+"                            Qinq interface for vbui101  :     "+qinqvbui101(index));
 outputtext.append("\n\n"+"                            Qinq interface for vbui1700  :     "+qinqvbui1700(index));
 outputtext.append("\n\n"+"                            Qinq interface for vbui200  :     "+qinqvbui200(index));
 outputtext.append("\n\n"+"                            Qinq interface for vbui300  :     "+qinqvbui300(index));
 outputtext.append("\n\n"+"                            ER-vpn Interface                  :     "+String.valueOf(A[index][6]));
 outputtext.append("\n\n"+"                            ER-A int IP to bras               :     "+String.valueOf(A[index][7]));
 outputtext.append("\n\n"+"                            ER-B int IP to bras               :     "+String.valueOf(A[index][8]));

 count++;
 
if (count>=sInd.size())count=0;
         
    }
  ipCount=0;
  vlanCount=0;
   // freeVlanData=takenVlanFinder(iphostDataA)  ;  
    }//GEN-LAST:event_searchActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        
       msagIp.setText("");  vlanTag.setText("");  msagTag.setText(""); 
       vlan.setText("");    msag.setText("");     area.setText("");
       count=0; outputtext.setText(""); 
       searchIndex=-1;       
//       jPanel5.removeAll();jPanel5.repaint(); jPanel5.revalidate();
       
    }//GEN-LAST:event_clearActionPerformed

    private void msagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msagActionPerformed
        // TODO add your handling code here:      
    }//GEN-LAST:event_msagActionPerformed
   final Clipboard clip= Toolkit.getDefaultToolkit().getSystemClipboard();
 
    private void changevlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changevlanActionPerformed
        // TODO add your handling code here:
     
    }//GEN-LAST:event_changevlanActionPerformed

    private void findallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findallActionPerformed
    searchIndex=-1;msag.setText("");msagIp.setText("");vlan.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
    ArrayList<String> iphostData1=new ArrayList<>();        
     iphostData1=iphostDataA;       
    if(!(outputtext.getSelectedText()==null)) {
         searchItem.getEditor().setItem(outputtext.getSelectedText());         
        }
    ArrayList<Integer> findAllIndex=new ArrayList<>();
    String srch =((String)searchItem.getEditor().getItem()).trim();       
        for (int i = searchItem.getItemCount()-3; i >=0 ; i--) {
          if(searchItem.getItemAt(i).equals(srch)) searchItem.removeItemAt(i);          
        }        
        searchItem.insertItemAt(srch,0);      
        findAllIndex= searchIndex(iphostData1,srch);
        outputtext.setText("");
  if(!(findAllIndex.isEmpty())) {
        for(int A:findAllIndex){
        int fIndex=A;
        int bulky=-1;
        if(iphostData1.get(fIndex).contains("ip host")|| iphostData1.get(fIndex).contains("ip-host")||
                iphostData1.get(fIndex).contains("ip route")||iphostData1.get(fIndex).contains("ip policy")){
        outputtext.append("\n"+iphostData1.get(fIndex));
         }
       if(!(iphostData1.get(fIndex).contains("ip host") || iphostData1.get(fIndex).contains("ip-host")||
               iphostData1.get(fIndex).contains("ip route")||iphostData1.get(fIndex).contains("ip policy"))){
           int initiaL=-1; int finaL=-1;  
           
           for (int i =fIndex ; i < iphostData1.size(); i++) {
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      
                      finaL=i;
                      break;
                  }
              
                  
           }
           
           for (int i =fIndex ; i >=0; i--) {
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      initiaL=i;
                      break;
                  }
                   bulky=-1;
              if(iphostData1.get(i).contains("subscriber ipv4 vrf DATA") || 
                           iphostData1.get(i).contains("ipv4-access-list")||iphostData1.get(i).contains("user-dynamic-vlan")) bulky=i;
                  
                   }
            
           if(initiaL>-1&&finaL>0&&bulky==-1)
               for (int i = initiaL; i <=finaL; i++)
                     outputtext.append("\n"+iphostData1.get(i));
           
           if(bulky!=-1)   {
               outputtext.append(" \n$");
               outputtext.append("\n"+iphostData1.get(bulky));
               outputtext.append("\n"+iphostData1.get(fIndex));
               outputtext.append(" \n$");
           }            
        }
       
       
          String vpnSub=outputtext.getText(),IPALLOC="";
        int mIndex=-1; String VL="";
  
        if(vpnSub.contains("ip vrf forwarding")){
        
         ArrayList<String>  vpnSubData=vpnParamFind(outputtext.getText());  
          if(vpnSubData.size()>0) {
         if(vpnSubData.get(5).trim().equals("")&& vpnSubData.get(0).substring(vpnSubData.get(0).indexOf(".")+1,vpnSubData.get(0).length()-1).length()>=4) 
               VL=vpnSubData.get(0).substring(vpnSubData.get(0).indexOf(".")+1,vpnSubData.get(0).indexOf(".")+5);
         
          else VL=vpnSubData.get(5);
         
         if(vpnSubData.get(3).contains("0.0.0.0")) IPALLOC=getIpAllocFromInterface(vpnSubData.get(0),fIndex);
         else  IPALLOC=getIpAlloc(vpnSubData.get(3));
        
          mIndex=msagFinder(VL,vpnSubData.get(0).substring(0,vpnSubData.get(0).indexOf(".")),IPALLOC,"ER");
        
        }

         fillMsag(mIndex); 
     }
       
       
       }
         findRemark.setText("Count:  "+findAllIndex.size()+"  matches");findRemark.setHorizontalAlignment(JLabel.CENTER);
         
         
     }
       searchItem.getEditor().setItem(searchItem.getItemAt(0));
      findRemark.setText("Count:  "+findAllIndex.size()+"  matches"); findRemark.setHorizontalAlignment(JLabel.CENTER);       
      outputtext.setCaretPosition(0);
      
       //@@@@@@@@@@@     VPN      @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    }//GEN-LAST:event_findallActionPerformed

    private void findpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findpActionPerformed
      ArrayList<String> iphostData1=new ArrayList<>();
        ArrayList<String> temp=new ArrayList<>();
      iphostData1=iphostDataA;
       String  vpnInter="",vpnSub="",ErHostName="",vpnAll="";
        ArrayList<String> vpnData=new ArrayList<>();
        ArrayList<String> vpnSubData=new ArrayList<>();
     
       if(!(outputtext.getSelectedText()==null)) { 
         findCount=0;
         searchItem.getEditor().setItem(outputtext.getSelectedText());          
      }         
 
     String srch =((String)searchItem.getEditor().getItem()).trim();   
     findRemark.setText("");    
 for (int i = searchItem.getItemCount()-3; i >=0 ; i--) {
      if(searchItem.getItemAt(i).equals(srch)||(searchItem.getItemAt(i).equals(""))) searchItem.removeItemAt(i); 
            
   }  
  searchItem.insertItemAt(srch,0); 
 String range=srch;
      if((range.substring(0,1).equals("@"))&&(range.contains("-"))&&(range.contains("."))){
         outputtext.setText(" ");          
         String RANGE=range.substring(1,range.length());        
         ArrayList<String> data=freeIpFinder(RANGE);
         for(String s:data)outputtext.append(s+"\n");
         searchItem.insertItemAt(range,0);
      }  
      else if((range.toLowerCase().contains("br@"))){
         if((range.substring(0,3).toLowerCase().equals("br@"))){ 
         outputtext.setText(" ");          
         String RANGE=range.substring(3,range.length()); 
         int brPos=searchIndex(dataColls(brData,0),RANGE).get(findCount); 
         outputtext.append("\n\n\n");
         outputtext.append("\t\t\t          "+brData[brPos][0]+" - Bras "+"\n\n");
         outputtext.append("\t\t\t          "+brData[brPos][1]);         
         searchItem.insertItemAt(range,0);
         findCount++;
         if (findCount>=searchIndex(dataColls(brData,0),RANGE).size())findCount=0;
      }
      }
        else if((range.toLowerCase().contains("er@"))){
         if((range.substring(0,3).toLowerCase().equals("er@"))){ 
         outputtext.setText(" ");          
         String RANGE=range.substring(3,range.length()); 
         int erPos=searchIndex(dataColls(erData,0),RANGE).get(findCount);
         int noEr=str2num(erData[erPos][3]);
         outputtext.append("\n\n\n");
         outputtext.append("\t\t\t\t        "+erData[erPos][0]+" - ER"+"\n\n");
             for (int i = 0; i < noEr; i++) {
              outputtext.append("\t\t\t\t"+erData[erPos+i][1]+" = "+erData[erPos+i][2]+"\n");   
             }
         searchItem.insertItemAt(range,0);findCount++;
         if (findCount>=searchIndex(dataColls(erData,0),RANGE).size())findCount=0; 
      }
      }
      
         else if((range.toLowerCase().contains("sw@"))){
         if((range.substring(0,3).toLowerCase().equals("sw@"))){ 
         outputtext.setText(" ");          
         String RANGE=range.substring(3,range.length()); 
         int swPos=searchIndex(dataColls(swData,0),RANGE).get(findCount);
         int noSw=str2num(swData[swPos][3]);
         outputtext.append("\n\n\n");
         outputtext.append("\t\t\t\t        "+swData[swPos][0]+" - Switch"+"\n\n");
             for (int i = 0; i < noSw; i++) {
              outputtext.append("\t\t\t\t"+swData[swPos+i][1]+" = "+swData[swPos+i][2]+"\n");   
             }
         searchItem.insertItemAt(range,0);findCount++;
         if (findCount>=searchIndex(dataColls(swData,0),RANGE).size())findCount=0; 
      }//TANA smc tool
      }
        else if((range.toLowerCase().contains("ipa@"))){
         if((range.substring(0,4).toLowerCase().equals("ipa@"))&&(!(range.contains(".")))){ 
         outputtext.setText(" ");          
         String RANGE=range.substring(4,range.length()); 
         int ipaPos=searchIndex(dataColls(ipAllocationData,0),RANGE).get(findCount);        
         outputtext.append("\n\n\n");
         outputtext.append("\t\t\t        "+ipAllocationData[ipaPos][0]+" - IP-Allocation "+"\n\n");
         outputtext.append("\t\t\t                   "+ipAllocationData[ipaPos][1]+"\n\n");
          searchItem.insertItemAt(range,0);findCount++;
         if (findCount>=searchIndex(dataColls(ipAllocationData,0),RANGE).size())findCount=0; 
         }
         if((range.substring(0,4).toLowerCase().equals("ipa@"))&&(range.contains("."))){ 
         outputtext.setText(" ");          
         String RANGE=range.substring(4,range.length()); 
         int ipaPos=searchIndex(dataColls(ipAllocationData,1),RANGE).get(findCount);      
         outputtext.append("\n\n\n");
         outputtext.append("\t\t\t        "+ipAllocationData[ipaPos][0]+" - IP-Allocation  "+"\n\n");
         outputtext.append("\t\t\t                  "+ipAllocationData[ipaPos][1]+"\n\n");
         
         searchItem.insertItemAt(range,0);findCount++;
         if (findCount>=searchIndex(dataColls(ipAllocationData,1),RANGE).size())findCount=0; 
      }
      }
        else if(range.toLowerCase().contains("help@")){ 
          if(range.substring(0,5).toLowerCase().contains("help@"))  {
         outputtext.setText(" ");          
         String RANGE=range.substring(5,range.length()); 
         int fIndex=searchIndex(helpData,RANGE).get(findCount); 
           int initiaL=0; int finaL=0;
           for (int i =fIndex ; i < helpData.size(); i++) {
                  if(helpData.get(i).contains("%")){
                      finaL=i;break;
                  }                  
           }
            for (int i =fIndex ; i >=0; i--) {
                  if(helpData.get(i).contains("%")){
                      initiaL=i;break;
                  }                  
           }
           outputtext.setText("");
           if(initiaL>-1&&finaL>0){
           for (int i = initiaL; i <=finaL; i++) {             
           outputtext.append("\n"+helpData.get(i));  
           findRemark.setText("Count:  "+searchIndex(helpData,RANGE).size()+"  matches");findRemark.setHorizontalAlignment(JLabel.CENTER);
           }
           }
           findCount++;
         if (findCount>=searchIndex(helpData,RANGE).size())findCount=0;         
        } 
       }
      else{
   ArrayList <String> itemData=new ArrayList<>();
     if(iphostData1.size()!=0){

  findIndex=new ArrayList <>();
  findIndex=searchIndex(iphostData1,srch);
  if(findIndex.isEmpty()){findRemark.setText("Count:  "+findIndex.size()+"  matches");findRemark.setHorizontalAlignment(JLabel.CENTER);}
  
//////////// if(!(findtext.getText().equals("")) ) {
if((findIndex.isEmpty()) ){
 msagIp.setText("  "); 
 msag.setText(""); 
 vlan.setText("");
 vlanTag.setText("");
 msagTag.setText("");
 area.setText("");
 outputtext.setText("");
 searchIndex=-1;
}
 if(!(findIndex.isEmpty()) ) {
    outputtext.setText("");
//////////// findIndex=searchIndex(iphostData,findtext.getText());
 int fIndex,bulky=-1,mIndex=-1;
 if(findIndex.size()==1)findCount=0;
  
  fIndex=findIndex.get(findCount);//System.out.println(iphostData.size());
  outputtext.setText("");
  searchIndex=-1;msag.setText("");msagIp.setText("");vlan.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
 if((iphostData1.get(fIndex).contains("ip host")||iphostData1.get(fIndex).contains("ip-host")||iphostData1.get(fIndex).contains("ip route")||
               iphostData1.get(fIndex).contains("ip policy"))) {
if(iphostData1.get(fIndex).contains("ip-host")||iphostData1.get(fIndex).contains("ip host"))
                vpnSubData=bbParamFind(iphostData1.get(fIndex)); 
if(vpnSubData.size()>0){
     if((vpnSubData.get(1).contains("slot"))&&!(vpnSubData.get(1).contains("smartgroup")))
        mIndex=msagFinder(vpnSubData.get(4),(vpnSubData.get(2)+"/"+vpnSubData.get(3)),getIpAlloc(vpnSubData.get(0)),"BRAS");
           
    if((vpnSubData.get(1).contains("smartgroup"))&&!(vpnSubData.get(1).contains("slot")))
         mIndex=msagFinder(vpnSubData.get(2),midString(vpnSubData.get(1),"smartgroup","."),getIpAlloc(vpnSubData.get(0)),"BRAS");
} 
    fillMsag(mIndex);     
           outputtext.setText("");
           outputtext.append("\n\n"+"  "+iphostData1.get(fIndex));
           findRemark.setText("Count:  "+(findCount+1)+" / "+findIndex.size()+"  matches");
           findRemark.setHorizontalAlignment(JLabel.CENTER);               
}

 //@@@@@@@@@@@@@@  VPN + PUBLIC @@@@@@@@@@@@@@@@@
 
       if(!(iphostData1.get(fIndex).contains("ip host")||iphostData1.get(fIndex).contains("ip-host")||iphostData1.get(fIndex).contains("ip route")||
               iphostData1.get(fIndex).contains("ip policy")||iphostData1.get(fIndex).contains("permit ip"))){
                  
//           int initiaL=-1; int finaL=-1;
           int initiaL=0; int finaL=0;
         
           for (int i =fIndex ; i < iphostData1.size(); i++) {
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      finaL=i;break;
                  }                  
           }
            for (int i =fIndex ; i >=0; i--) {
                
                if((iphostData1.get(i).contains("interface "))&&(iphostData1.get(i).contains("gei-")))
                     vpnInter=(iphostData1.get(i));
                
                
                  if(iphostData1.get(i).contains("!") || iphostData1.get(i).contains("$")){
                      initiaL=i;break;
                  }    
                  
                  bulky=-1;
              if(iphostData1.get(i).contains("subscriber ipv4 vrf DATA") || 
                           iphostData1.get(i).contains("ipv4-access-list")||iphostData1.get(i).contains("user-dynamic-vlan"))bulky=i;
           }
//            temp=new ArrayList<>();
           outputtext.setText("");
           if(initiaL>-1&&finaL>0&&bulky==-1){
           for (int i = initiaL; i <=finaL; i++) {             
           outputtext.append("\n"+iphostData1.get(i)); 
//           temp.add("\n"+iphostData1.get(i));
           findRemark.setText("Count:  "+(findCount+1)+" / "+findIndex.size()+"  matches");
           findRemark.setHorizontalAlignment(JLabel.CENTER);}
           }
            if(bulky!=-1)   {
               outputtext.append(" \n$");
//               temp.add("\n$");
               outputtext.append("\n"+iphostData1.get(bulky));
//               temp.add("\n"+iphostData1.get(bulky));
               outputtext.append("\n"+iphostData1.get(fIndex));
               outputtext.append(" \n$");
//               temp.add(" \n$");
           } 
           
           vpnSub=outputtext.getText();
           
           
         //@@@@@@@@@@@@@  PUBLIC @@@@@@@@@@@@@@@@@@@@@@@
         
    if(vpnSub.contains("internal-vlan ")&&vpnSub.contains("external-vlan ")){
              vpnSubData=ppParamFind(vpnSub); 
          if(vpnSubData.size()>0){
                if((vpnSubData.get(0).contains("gei_"))&&!(vpnSubData.get(0).contains("smartgroup")))
                   mIndex=msagFinder(vpnSubData.get(2),(midString(vpnSubData.get(0),"gei_",".")),getIpAlloc(vpnSubData.get(3)),"BRAS");
               
               if((vpnSubData.get(0).contains("smartgroup"))&&!(vpnSubData.get(0).contains("gei_")))
                   mIndex=msagFinder(vpnSubData.get(2),midString(vpnSubData.get(0),"smartgroup","."),getIpAlloc(vpnSubData.get(3)),"BRAS");
           }
           fillMsag(mIndex);                 
         }
    
    //@@@@@@@@@@@     VPN      @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    if(vpnSub.contains("ip vrf forwarding")||vpnSub.contains("external-vlanid")){
        
          vpnSubData=vpnParamFind(vpnSub);  
        //System.out.println(vpnSub);
         String VL="",IPALLOC="";
////////     if(vpnSubData.size()>0) 
////////         if((vpnSubData.get(0).substring(vpnSubData.get(0).indexOf(".")+1,vpnSubData.get(0).length()-1)).length()>=4)
////////         mIndex=msagFinder(vpnSubData.get(0).substring(vpnSubData.get(0).indexOf(".")+1,vpnSubData.get(0).indexOf(".")+5),
////////                 vpnSubData.get(0).substring(0,vpnSubData.get(0).indexOf(".")),
////////                 getIpAllocFromInterface(vpnSubData.get(0),fIndex),"ER");
////////           fillMsag(mIndex); 
////////           
    
       if(vpnSubData.size()>0) {
         if(vpnSubData.get(5).trim().equals("")&& vpnSubData.get(0).substring(vpnSubData.get(0).indexOf(".")+1,vpnSubData.get(0).length()-1).length()>=4) 
              
             VL=vpnSubData.get(0).substring(vpnSubData.get(0).indexOf(".")+1,vpnSubData.get(0).indexOf(".")+5);
         
          else {
             VL=vpnSubData.get(5);
             ;
         }
         
         if(vpnSubData.get(3).contains("0.0.0.0")) IPALLOC=getIpAllocFromInterface(vpnSubData.get(0),fIndex);
         else  IPALLOC=getIpAlloc(vpnSubData.get(3));
        
          mIndex=msagFinder(VL,vpnSubData.get(0).substring(0,vpnSubData.get(0).indexOf(".")),IPALLOC,"ER");
        
        }

         fillMsag(mIndex); 
    
    
    }
    
    
       }
  
   }
     for (int i = 0; i <=searchItem.getItemCount()-3 ; i++) {
            itemData.add(searchItem.getItemAt(i)); 
            if(i>24) break;
        } 
   xmlListWrite(itemData,"FIND_HISTORY"); 
   if((searchItem.getItemCount()-3)>24)searchItem.removeItemAt(searchItem.getItemCount()-3);
   
  outputtext.setCaretPosition(0);
    findC=findCount;        
    findCount++;
if (findCount>=findIndex.size())findCount=0; 
   }else outputtext.setText("\n\n\n\n\n             <<<                                                 "
           + "                                     Browse  for  Back-UP  from  ER and/or BRAS  !!!!"
           + "                                                   >>>"); 
  }
   searchItem.getEditor().setItem(searchItem.getItemAt(0));
   outputtext.setCaretPosition(0);
    }//GEN-LAST:event_findpActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
       changevlan.setText("");
//       findtext.setText("");
       searchItem.getEditor().setItem("");
       outputtext.setText("");findCount=0;count=0;vCount=0;
       findIndex=new ArrayList<>();OTR=new ArrayList<>();
       findRemark.setText(""); 
       rSrchCount=0;
       rSrchIndex=-1;
       rSearch.setText("");rSrchRemark.setText("");
      
    }//GEN-LAST:event_resetActionPerformed


    private void replacespeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replacespeedActionPerformed
           
           String speed=(changevlan.getText().trim());
           String textotr="";
           textotr=outputtext.getText();
           outputtext.setText("");
           outputtext.append("\n\n"+"     "+textotr);
           String resumed=changeSpeed(textotr,speed);
           outputtext.append("\n\n\n");
           outputtext.append("\n"+resumed);
           

    }//GEN-LAST:event_replacespeedActionPerformed

    private void blockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockActionPerformed
     
           OTR=new ArrayList<>();
           findRemark.setText("");
           String textot;           
           textot=outputtext.getText().trim();
           outputtext.setText("");
           outputtext.append("  \n\n"+"       "+textot);
           ArrayList <String> blocked=twoWayBlock(textot);
           outputtext.append("\n\n");
          for(String R:blocked)outputtext.append("\n"+"   "+R);
    }//GEN-LAST:event_blockActionPerformed

    private void terminateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminateActionPerformed
           OTR=new ArrayList<>();
           String textot=outputtext.getText().trim();           
           findRemark.setText("");
           outputtext.setText("");
           outputtext.append("\n\n"+"       "+textot);
           ArrayList <String> terminated=termination(textot);           
           outputtext.append("\n\n");
           for(String R:terminated)outputtext.append("\n"+"   "+R);
        
    }//GEN-LAST:event_terminateActionPerformed

    private void replacevlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replacevlanActionPerformed
      //@@@@@@@@@@@@@    INTERNET   @@@@@@@@@@@@@@@@@@
        
        if(outputtext.getText().contains("ip-host")||outputtext.getText().contains("ip host")) {
           String vlanIn=changevlan.getText();
           String textotr="";
           textotr=outputtext.getText();
            
            outputtext.setText("");
            outputtext.append(textotr);
           outputtext.append("\n\n");
         
           ArrayList <String> cVlan=changeVlan(textotr,vlanIn);
           if(outputtext.getText().contains("sec-vlan") ) updateTask(cVlan.get(4));
           if(outputtext.getText().contains("second-vlan") ) updateTask(cVlan.get(3));
          // outputtext.setText("");
       
          // if(!outputtext.getText().contains("sec-vlan") ) outputtext.append("\n\n"+textotr);
           
         
          for(String R:cVlan)outputtext.append("\n"+"   "+R);           
          outputtext.append("\n\n");         }
        
      
       /// @@@@@@@@@  VPN @@@@@@@@@@@@@@
       
         if((outputtext.getText().contains("interface smartgroup"))&&!(outputtext.getText().contains("ip host"))&&!(outputtext.getText().contains("ip-host"))&&(outputtext.getText().contains("ip vrf forwarding"))){
             
             
             ArrayList <String> Data=vpnParamFind(outputtext.getText());         
             String noInter="",dataInterface="",exVlan="",finalConfig="",ipAlloc="";
             
             
         String ipPolicy="",subId="",natDomain="";         
        
             
           if(searchIndex==-1){
                 exVlan=Data.get(5);
                 dataInterface=Data.get(0).substring(0,Data.get(0).indexOf(".")+1)+exVlan+changevlan.getText();
                 subId="1";
                 natDomain="2";
             }
           if(searchIndex!=-1){
                 exVlan=A[searchIndex][2];
                 dataInterface= A[searchIndex][6]+"."+A[searchIndex][2]+changevlan.getText();
                 subId=A[searchIndex][19];
                 natDomain=A[searchIndex][20];
             }
           
            if((Data.get(2).trim().equals("DATA"))){
//                  &&(vpnLanIp.getText().trim().equals(""))){
            ipPolicy="\n   $"+
                     "\n  cgn"+
                      "\n   subscriber ipv4 vrf DATA subscriber-id "+subId+" nat-domain "+natDomain+
                      "\n    interface "+dataInterface+
                      "\n   $"+
                      "\n   $";              
          }
         else ipPolicy=""; 
           
           
             
             noInter=("    config t\n"+
                     "    no interface "+Data.get(0)+"\n"+
                     "    $\n"+
                     "    $\n" );
           
         ipAlloc=getIpAlloc(Data.get(3));
          ArrayList<String> outPutVc=vpnConfig(Data.get(1),Data.get(6),changevlan.getText(),exVlan,Data.get(3),"","",Data.get(2),Data.get(7),dataInterface);
           if((Data.get(7).trim().equals(""))){
               outPutVc.add(ipPolicy);
               finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER          --               @M@$@L@L@A@  \n\n\n"+
                              noInter+"   config t\n"+outPutVc.get(0)+outPutVc.get(2)+"\n\n\n\n" );
               updateTask("!\n"+outPutVc.get(0)+outPutVc.get(2)+"\n!"); 
           }  
    
       else if(!(Data.get(7).trim().equals(""))){
              outPutVc.add(ipPolicy);
              finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - A          --               @M@$@L@L@A@  \n\n\n"+              
                           noInter+"   config t\n"+outPutVc.get(0)+outPutVc.get(2)+
                           "\n                   @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - B          --               @M@$@L@L@A@  \n\n\n\n"+
                           noInter+"   config t\n"+outPutVc.get(1)+outPutVc.get(2));
       updateTask("!\n"+outPutVc.get(0)+"\n!\n"+outPutVc.get(1)+outPutVc.get(2)+"\n!"); 
       }
          outputtext.setText("");
          outputtext.append(finalConfig);
          
         
         }
 
         
           /// @@@@@@@@@  VPN @@@@@@@@@@@@@@
       
         if(!(outputtext.getText().contains("interface smartgroup"))&&!(outputtext.getText().contains("ip-host"))&&!(outputtext.getText().contains("ip host"))&&(outputtext.getText().contains("ip vrf forwarding"))){
             
             
             ArrayList <String> Data=vpnParamFind(outputtext.getText());         
             String noInter="",dataInterface="",exVlan="",finalConfig="",ipAlloc="";
             
             if(searchIndex==-1){
                 exVlan=Data.get(5);
                 dataInterface=Data.get(0).substring(0,Data.get(0).indexOf(".")+1)+exVlan+changevlan.getText();
             }
             if(searchIndex!=-1){
                 exVlan=A[searchIndex][2];
                 dataInterface= A[searchIndex][6]+"."+A[searchIndex][2]+changevlan.getText();
             }
             
             noInter=("    config t\n"+
                     "    no interface " + Data.get(0)+"\n"+
                     "    !\n"+
                     "    !\n" );
           
         ipAlloc=getIpAlloc(Data.get(3));
          ArrayList<String> outPutVc=vpnConfig(Data.get(1),Data.get(6),changevlan.getText(),exVlan,Data.get(3),"","",Data.get(2),Data.get(7),dataInterface);
           if((Data.get(7).trim().equals(""))){
               finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER          --               @M@$@L@L@A@  \n\n\n"+
                              noInter+"   config t\n"+outPutVc.get(0)+"\n\n\n\n" );
               updateTask("!\n"+outPutVc.get(0)+"\n!"); 
           }  
    
       else if(!(Data.get(7).trim().equals(""))){
              finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - A          --               @M@$@L@L@A@  \n\n\n"+              
                           noInter+"   config t\n"+outPutVc.get(0)+
                           "\n                   @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - B          --               @M@$@L@L@A@  \n\n\n\n"+noInter+"   config t\n"+outPutVc.get(1));
       updateTask("!\n"+outPutVc.get(0)+"\n!\n"+outPutVc.get(1)+"\n!"); 
       }
          outputtext.setText("");
          outputtext.append(finalConfig);
          outputtext.setCaretPosition(0);
         
         }
////@@@@@@@@@@@@@@@@@@@@  PUBLIc @@@@@@@@@@@@@@@@
         
       
   if((outputtext.getText().contains("external-vlan ")&&outputtext.getText().contains("internal-vlan "))&&(!outputtext.getText().contains("ip vrf forwarding"))){
        String SMARTG="",dataPInterface="",exVlan="",noInter,finalOut="",erAip="",erBip="";     
        ArrayList <String> Data=ppParamFind(outputtext.getText());
         
       if(searchIndex!=-1){
          SMARTG=smartgroup(searchIndex)+A[searchIndex][2]+changevlan.getText(); 
          dataPInterface=A[searchIndex][6]+"."+A[searchIndex][2]+changevlan.getText();
          exVlan=A[searchIndex][2];
          erAip=A[searchIndex][7];
          erBip=A[searchIndex][8];
         }
       if(searchIndex==-1){
          exVlan=Data.get(2);
          SMARTG=Data.get(0).substring(0,Data.get(0).indexOf(".")+1)+exVlan+changevlan.getText(); 
          dataPInterface="";
          erAip="";
          erBip="";
         }
          
          noInter=("    config t\n"+
                     "    no interface "+Data.get(0)+"\n"+
                     "    !\n"+
                     "    !\n" );
          
           ArrayList<String>  pOut=pConfig(Data.get(4),Data.get(5),changevlan.getText(),exVlan,Data.get(3),"","",SMARTG,dataPInterface,erAip,erBip);
           
          
            finalOut= ("    config t\n"+pOut.get(0));         
            
            outputtext.setText("");
            outputtext.append("\n\n"+noInter+finalOut);  
            updateTask("!\n"+pOut.get(0)+"\n!");
           
         }

          
    }//GEN-LAST:event_replacevlanActionPerformed

    private void msagIpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_msagIpFocusGained
 
       msagIp.selectAll();

    }//GEN-LAST:event_msagIpFocusGained

    private void outputtextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_outputtextFocusGained
//            msagIp.selectAll();

    }//GEN-LAST:event_outputtextFocusGained

    private void changevlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_changevlanFocusGained
       changevlan.selectAll();            
    }//GEN-LAST:event_changevlanFocusGained

    private void msagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_msagFocusGained
          msag.selectAll();
         vlan.setText("");
         msagIp.setText("");
        area.setText("");msagTag.setText("");vlanTag.setText("");
        searchIndex=-1;
    }//GEN-LAST:event_msagFocusGained

    private void vlanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vlanFocusLost

    }//GEN-LAST:event_vlanFocusLost

    private void vlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vlanFocusGained
        vlan.selectAll();
        msag.setText("");  
        msagIp.setText("");
        area.setText("");msagTag.setText("");vlanTag.setText("");
        searchIndex=-1;
    }//GEN-LAST:event_vlanFocusGained

    
    
    
    private void outputtextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_outputtextFocusLost
//    try{
//        String x=outputtext.getSelectedText();
//        StringSelection dat =new StringSelection (x);
//        clip.setContents(dat,dat); 
//        String svlan="";
//        if(clip.getContents(clip).isDataFlavorSupported(DataFlavor.stringFlavor)) 
//               
//        {svlan=((String)(clip.getContents(clip).getTransferData(DataFlavor.stringFlavor))).trim();}
//    }catch (Exception e){}
    }//GEN-LAST:event_outputtextFocusLost

    private void searchItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchItemActionPerformed
//        String[] select = new String []{(String)searchItem.getSelectedItem()};
//        System.out.println(select[0]);
////        searchItem.insertItemAt(select[0],0);
        

    }//GEN-LAST:event_searchItemActionPerformed

    private void searchItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchItemFocusGained
        
    
    }//GEN-LAST:event_searchItemFocusGained

    private void stickyNoteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stickyNoteFocusLost
        // TODO add your handling code here:
        xmlStringWrite(stickyNote.getText(),"Sticky");      

    }//GEN-LAST:event_stickyNoteFocusLost

    private void stickyNoteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stickyNoteFocusGained
//        stickyNote.setText(xmlStringRead("Sticky"));
         xmlStringWrite(stickyNote.getText(),"Sticky");
    }//GEN-LAST:event_stickyNoteFocusGained

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed

ArrayList<ArrayList<String>> report=xmlArrayRead("Report");
ArrayList<ArrayList<String>> backup=xmlArrayRead("BackUp");
    ArrayList<String> data= new ArrayList<>();
     
          switch (selector.getSelectedIndex()) {
            case 1:
              data.add(bbName.getText().toUpperCase().trim());
              if(activity.equals(""))data.add("New Connection");else data.add(activity);
              data.add(bbAccess.getText().toUpperCase().trim());
              data.add(A[searchIndex][3]);data.add("Broadband Internet");data.add(""+cDate);data.add(A[searchIndex][0]);
              if(bbSpeed.getText().equals("64")||bbSpeed.getText().equals("256")||bbSpeed.getText().equals("512"))
              data.add(bbSpeed.getText().trim()+" K");
              else data.add(bbSpeed.getText().trim()+" M");
              data.add(bbWanIp.getText().trim());data.add(bbVlan.getText().trim());data.add("");data.add("");          
            break;            
            case 2:
              data.add(pName.getText().toUpperCase().trim());data.add(" PUBLIC ");data.add(pAccess.getText().toUpperCase().trim());
              data.add(A[searchIndex][3]);data.add("Broadband Internet");data.add(""+cDate);data.add(A[searchIndex][0]);
              if(pSpeed.getText().equals("64")||pSpeed.getText().equals("256")||pSpeed.getText().equals("512"))
              data.add(pSpeed.getText().toUpperCase()+" K");
              else data.add(pSpeed.getText().toUpperCase()+" M");
              if((subIp(pWanIp.getText(),3)>63)&&(subIp(pWanIp.getText(),3)<96)) data.add(hostIp(pWanIp.getText(),30,2)); 
              if((subIp(pWanIp.getText(),3)>191)&&(subIp(pWanIp.getText(),3)<=255)) data.add(hostIp(pWanIp.getText(),29,4));              
              data.add(pVlan.getText().trim());data.add(networkIp(pLanIp.getText(),pSubnet.getText())+" /"+subnet(pSubnet.getText()));data.add("");                
            break;                
            case 3:
              data.add(vpnName.getText().trim().toUpperCase());
              if(activity.equals(""))data.add("New Connection");else data.add(activity);
              data.add(vpnAccess.getText().toUpperCase().trim());
              data.add(A[searchIndex][3]);data.add("Broadband VPN");data.add(""+cDate);data.add(A[searchIndex][0]);
              if(vpnSpeed.getText().equals("64")||vpnSpeed.getText().equals("66")||vpnSpeed.getText().equals("256")||vpnSpeed.getText().equals("512"))
              data.add(vpnSpeed.getText().toUpperCase()+" K");
              else data.add(vpnSpeed.getText().toUpperCase()+" M");
              data.add(hostIp(vpnWanIp.getText(),29,4)); data.add(vpnVlan.getText().trim());
              data.add(networkIp(vpnLanIp.getText(),vpnSubnet.getText())+" /"+subnet(vpnSubnet.getText()));data.add("");                
            break;
          
        }        
       backup.add(data);
       report.add(data);       
      xmlArrayWrite(backup,"BackUp");
      xmlArrayWrite(report,"Report");
      save.setEnabled(false);save.setBackground(Color.white);
      activity="";
    }//GEN-LAST:event_saveActionPerformed

        
    private void oltResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oltResetActionPerformed
        // TODO add your handling code here:
        oltVlan.setText("");oltSpeed.setText("");oltGemPort.setText("");
//        outputtext.setText("");
    }//GEN-LAST:event_oltResetActionPerformed

    private void oltRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oltRunActionPerformed
        outputtext.setText(" ");
        if(searchIndex==-1)outputtext.setText(noMsag());
        else{
            String config =String.join("\n"
                ,"--------- create ont-line profile ---------------\n\n"
                ,"         ont-lineprofile gpon profile-id O-LPID\n "
                ,"         tcont TCONT dba-profile-id D-PID\n"
                ,"         gem add GM-PRT eth tcont TCONT\n"
                ,"         gem mapping GM-PRT 1 vlan INNER\n"
                ,"         commit"
                ,"\n\n-------------  service port -----------\n\n"
                ,"   service-port vlan VLAN gpon GP-PRT ont GP-ONT gemport GM-PRT multi-service user-vlan INNER tag-transform translate-and-add inner-vlan INNER inbound traffic-table index O-TTI outbound traffic-table index O-TTI"
                ,"\n" );
        
        int ind=-1;
        for (int ii = 0; ii < oltTt.length; ii++) {
            if(oltTt[ii][0].equals(oltSpeed.getText())) {
                ind=ii;
            }
        }
        String oltTrafficIndex=oltTt[ind][1];
        String[] exp={"VLAN" ,"INNER" ,"O-LPID" ,"TCONT" ,"D-PID" ,"GM-PRT" ,"GP-ONT" ,"O-TTI" ,"GP-PRT"};
        String[] rep={A[searchIndex][2],oltVlan.getText(),A[searchIndex][15],A[searchIndex][14],A[searchIndex][13],oltGemPort.getText(),A[searchIndex][12],oltTrafficIndex,A[searchIndex][11]};
        for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]);
        outputtext.append("\n\n"+config);
        }
        mduVlan.setText(oltVlan.getText());
        mduSpeed.setText(oltSpeed.getText());

    }//GEN-LAST:event_oltRunActionPerformed

    private void mduResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mduResetActionPerformed
        // TODO add your handling code here:
        mduVlan.setText("");mduSpeed.setText("");mduPort.setText("");
//        outputtext.setText("");
    }//GEN-LAST:event_mduResetActionPerformed

    private void mduRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mduRunActionPerformed
        outputtext.setText(" ");
        if(searchIndex==-1)outputtext.setText(noMsag());
        else{
            String config=String.join("\n"
                , "--------  add vlan on MDU -------\n"
                ,"         vlan INNER smart"
                ,"         vlan attrib INNER common"
                ,"         port vlan INNER 0/0 0"
                ,"\n\n------- service port -------"
                ,"\n           service-port vlan INNER adsl PORT vpi 8 vci 81 multi-service user-vlan untagged inbound traffic-table index M-TTI outbound traffic-table index M-TTI"
            );
            //      ,"\n\n\n\n-------- Optional (only if trafic table is not created)----- to Creat trafic table----------"
            //      ,"\n         traffic table ip index (INDEX) cir SPEED  pir SPEED priority 0 priority-policy local-Setting "
            //      ,""
            //      ,"");

        String[] exp={"INNER" ,"PORT" ,"M-TTI"};
        String[]rep={ mduVlan.getText(),mduPort.getText(),mduSpeed.getText()};

        for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]);
        outputtext.append("\n\n"+config);
        }
        oltVlan.setText(mduVlan.getText());
        oltSpeed.setText(mduSpeed.getText());
    }//GEN-LAST:event_mduRunActionPerformed

    private void mduPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mduPortFocusGained
        mduPort.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_mduPortFocusGained

    private void mduVlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mduVlanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mduVlanActionPerformed

    private void mduVlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mduVlanFocusGained
        mduVlan.selectAll();  // TODO add your handling code here:
    }//GEN-LAST:event_mduVlanFocusGained

    private void mduSpeedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mduSpeedFocusGained
        mduSpeed.selectAll();  // TODO add your handling code here:
    }//GEN-LAST:event_mduSpeedFocusGained

    private void oltSpeedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oltSpeedFocusGained
        oltSpeed.selectAll();   // TODO add your handling code here:
    }//GEN-LAST:event_oltSpeedFocusGained

    private void oltGemPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oltGemPortFocusGained
        oltGemPort.selectAll();  // TODO add your handling code here:
    }//GEN-LAST:event_oltGemPortFocusGained

    private void oltVlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oltVlanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oltVlanActionPerformed

    private void oltVlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oltVlanFocusGained
        oltVlan.selectAll();  // TODO add your handling code here:
    }//GEN-LAST:event_oltVlanFocusGained

    private void vpnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vpnMouseClicked
 
    }//GEN-LAST:event_vpnMouseClicked

    private void vpnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vpnResetActionPerformed
        // TODO add your handling code here:
//        outputtext.setText("");
        vpnName.setText("");vpnAccess.setText("");vpnSpeed.setText("");
        vpnVlan.setText("");vpnWanIp.setText("");vpnLanIp.setText("");vpnSubnet.setText("");
        vpnVrf.setText("");vpnVrrp.setText("");vlanCount=0;ipCount=0;
        save.setEnabled(false);save.setBackground(Color.white);
    }//GEN-LAST:event_vpnResetActionPerformed

    private void vpnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vpnRunActionPerformed
        // TODO add your handling code here:
        outputtext.setText("");
        vlanCount=0;ipCount=0;
        
       if(searchIndex==-1)outputtext.setText(noMsag());
        else{
       if(A[searchIndex][6].contains("smartgroup")){
        String desc="";
        String vlan;
        if(vpnVlan.getText().equals("")) vlan="0000";else vlan=vpnVlan.getText();
        String dataInterface=(A[searchIndex][6]+"."+A[searchIndex][2]+vlan);
        if(!(vpnName.getText().equals(""))&& !(vpnAccess.getText().equals("")))
            desc=(vpnAccess.getText().trim()+"_"+vpnName.getText()).trim().toUpperCase()+"_"+today();
        else if(vpnAccess.getText().equals("")) desc=vpnName.getText().trim().toUpperCase()+"_"+today();//replaceAll("\\s++","_");
        else if(vpnName.getText().equals("")) desc=vpnAccess.getText().trim().toUpperCase()+"_"+today();//replaceAll("\\s++","_");
//        if(searchIndex==-1)outputtext.setText(noMsag());
//        else{
            //String cName=vpnName.getText().trim().toUpperCase().replaceAll("\\s++","_");//  replaceAll("\\s++","_").....replace all white space with "_"
//            outputtext.append(vpnConfig(desc,vpnSpeed.getText(),vpnVlan.getText(),vpnWanIp.getText(),vpnLanIp.getText(),vpnSubnet.getText(),vpnVrf.getText(),vpnVrrp.getText(),dataInterface));
            
          String finalConfig="",ipAlloc="",ipPolicy="",DATAINTERFACE="",exVlan="";
//          DATAINTERFACE="smartgroup"+A[searchIndex][6]+"."+A[searchIndex][2]+vpnVlan.getText();
          if((vpnVrf.getText().trim().equals("DATA"))){
//                  &&(vpnLanIp.getText().trim().equals(""))){
             ipPolicy="\n   $"+
                     "\n  cgn"+
                      "\n   subscriber ipv4 vrf DATA subscriber-id "+A[searchIndex][19]+" nat-domain "+A[searchIndex][20]+
                      "\n    interface "+dataInterface+
                      "\n   $"+
                      "\n   $";
              
          }
          else ipPolicy="";
          ipAlloc=getIpAlloc(vpnWanIp.getText());
          exVlan=A[searchIndex][2];
          ArrayList<String> outPutVc=vpnConfig(desc,vpnSpeed.getText().trim(),vpnVlan.getText().trim(),exVlan,
                  vpnWanIp.getText().trim(),vpnLanIp.getText().trim(),vpnSubnet.getText().trim(),
                  vpnVrf.getText().trim(),vpnVrrp.getText().trim(),dataInterface.trim());
      
          if((vpnVrrp.getText().trim().equals(""))){
                
           finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER          --               @M@$@L@L@A@  \n\n\n"+
                  "   config t\n"+ outPutVc.get(0)+ipPolicy+"\n\n\n\n" );
                  updateTask("!\n"+outPutVc.get(0)+ipPolicy+"\n!"); 
           }  
    
       else if(!(vpnVrrp.getText().trim().equals(""))){
               
           finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - A          --               @M@$@L@L@A@  \n\n\n"+              
                 "   config t\n"+outPutVc.get(0)+ipPolicy+
                  "\n                   @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - B          --               @M@$@L@L@A@  \n\n\n\n"+"   config t\n"+outPutVc.get(1)+ipPolicy);
                updateTask("!\n"+outPutVc.get(0)+"\n!\n"+outPutVc.get(1)+ipPolicy+"\n!"); 
          }
         
          
          outputtext.append(finalConfig) ; 
            
            save.setEnabled(true);
            save.setBackground(Color.blue);
            save.setForeground(Color.white);
//            updateTask(outputtext.getText());
       }
       if(!A[searchIndex][6].contains("smartgroup")){
        String desc="";
        String vlan;
        if(vpnVlan.getText().equals("")) vlan="0000";else vlan=vpnVlan.getText();
        String dataInterface=(A[searchIndex][6]+"."+A[searchIndex][2]+vlan);
        if(!(vpnName.getText().equals(""))&& !(vpnAccess.getText().equals("")))
            desc=(vpnAccess.getText().trim()+"_"+vpnName.getText()).trim().toUpperCase()+"_"+today();
        else if(vpnAccess.getText().equals("")) desc=vpnName.getText().trim().toUpperCase()+"_"+today();//replaceAll("\\s++","_");
        else if(vpnName.getText().equals("")) desc=vpnAccess.getText().trim().toUpperCase()+"_"+today();//replaceAll("\\s++","_");
//        if(searchIndex==-1)outputtext.setText(noMsag());
//        else{
            //String cName=vpnName.getText().trim().toUpperCase().replaceAll("\\s++","_");//  replaceAll("\\s++","_").....replace all white space with "_"
//            outputtext.append(vpnConfig(desc,vpnSpeed.getText(),vpnVlan.getText(),vpnWanIp.getText(),vpnLanIp.getText(),vpnSubnet.getText(),vpnVrf.getText(),vpnVrrp.getText(),dataInterface));
            
          String finalConfig="",ipAlloc="",ipPolicy="",DATAINTERFACE="",exVlan="";
          DATAINTERFACE=A[searchIndex][6]+"."+A[searchIndex][2]+vpnVlan.getText();
          if((vpnVrf.getText().trim().equals("DATA"))&&(vpnLanIp.getText().trim().equals("")))
              ipPolicy="   ip policy interface "+DATAINTERFACE +" route-map Toger";
          else ipPolicy="";
          ipAlloc=getIpAlloc(vpnWanIp.getText());
          exVlan=A[searchIndex][2];
          ArrayList<String> outPutVc=vpnConfig(desc.trim(),vpnSpeed.getText().trim(),vpnVlan.getText().trim(),exVlan,
                  vpnWanIp.getText().trim(),vpnLanIp.getText().trim(),vpnSubnet.getText().trim(),
                  vpnVrf.getText().trim(),vpnVrrp.getText().trim(),dataInterface.trim());
      
          if((vpnVrrp.getText().trim().equals(""))){
                
           finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER          --               @M@$@L@L@A@  \n\n\n"+
                  "   config t\n"+ outPutVc.get(0)+ipPolicy+"\n\n\n\n" );
                  updateTask("!\n"+outPutVc.get(0)+ipPolicy+"\n!"); 
           }  
    
       else if(!(vpnVrrp.getText().trim().equals(""))){
               
           finalConfig= ("                  @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - A          --               @M@$@L@L@A@  \n\n\n"+              
                 "   config t\n"+outPutVc.get(0)+ipPolicy+
                  "\n                   @T@A@M@I@R@U@       --   "+ipAlloc+" - ER - B          --               @M@$@L@L@A@  \n\n\n\n"+"   config t\n"+outPutVc.get(1)+ipPolicy);
                updateTask("!\n"+outPutVc.get(0)+"\n!\n"+outPutVc.get(1)+ipPolicy+"\n!"); 
          }
          outputtext.append(finalConfig) ; 
            
            save.setEnabled(true);
            save.setBackground(Color.blue);
            save.setForeground(Color.white);
//            updateTask(outputtext.getText());
              
           
       }
       
         }
        outputtext.setCaretPosition(0);

    }//GEN-LAST:event_vpnRunActionPerformed

    private void vpnNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnNameFocusGained
        vpnName.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_vpnNameFocusGained

    private void vpnVrrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnVrrpFocusGained
        // TODO add your handling code here:
        vpnVrrp.selectAll();
    }//GEN-LAST:event_vpnVrrpFocusGained

    private void vpnVrfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnVrfFocusGained
        vpnVrf.selectAll();// TODO add your handling code here:
    }//GEN-LAST:event_vpnVrfFocusGained

    private void vpnSubnetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnSubnetFocusLost
        vpnSubnet.setText(subNetMask(vpnSubnet.getText().trim()));        // TODO add your handling code here:
    }//GEN-LAST:event_vpnSubnetFocusLost

    private void vpnSubnetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnSubnetFocusGained
        vpnSubnet.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_vpnSubnetFocusGained

    private void vpnLanIpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnLanIpFocusLost
        if(!(vpnSubnet.getText().equals(""))) vpnLanIp.setText(networkIp(vpnLanIp.getText().trim(),subNetMask(vpnSubnet.getText().trim())));        // TODO add your handling code here:
    }//GEN-LAST:event_vpnLanIpFocusLost

    private void vpnLanIpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnLanIpFocusGained
        vpnLanIp.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_vpnLanIpFocusGained

    private void vpnWanIpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnWanIpFocusLost
     ipCount=0;   vpnWanIp.setText(hostIp(vpnWanIp.getText().trim(),29,0));        // TODO add your handling code here:
    }//GEN-LAST:event_vpnWanIpFocusLost

    private void vpnWanIpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnWanIpFocusGained
      ipCount=0;  vpnWanIp.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_vpnWanIpFocusGained

    private void vpnVlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnVlanFocusGained
        vpnVlan.selectAll(); // TODO add your handling code here:
        vlanCount=0;ipCount=0;
    }//GEN-LAST:event_vpnVlanFocusGained

    private void vpnSpeedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnSpeedFocusGained
        vpnSpeed.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_vpnSpeedFocusGained

    private void vpnAccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vpnAccessActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vpnAccessActionPerformed

    private void vpnAccessFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnAccessFocusGained
        vpnAccess.selectAll();// TODO add your handling code here:
    }//GEN-LAST:event_vpnAccessFocusGained

    private void ppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ppMouseClicked
  
    }//GEN-LAST:event_ppMouseClicked

    private void pRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pRunActionPerformed
        
        outputtext.setText("");
        vlanCount=0;ipCount=0;
        String desc="",SMARTG="",dataInterface="",erAip="",erBip="",exVlan="";
        if(!(pName.getText().equals(""))&& !(pAccess.getText().equals("")))desc=(pAccess.getText().trim()+"_"+pName.getText()).trim().toUpperCase()+"_"+today();
        else if(pAccess.getText().equals("")) desc=pName.getText().trim().toUpperCase()+"_"+today();//replaceAll("\\s++","_");
        else if(pName.getText().equals("")) desc=pAccess.getText().trim().toUpperCase()+"_"+today();//replaceAll("\\s++","_");
        if(searchIndex==-1)outputtext.setText(noMsag());
        else{
            if(A[searchIndex][6].contains("smartgroup")){
            if(deleteIpHost.size()>0){
                outputtext.append(deleteIpHost.get(0)+"\n");outputtext.append(deleteIpHost.get(1)+"\n");outputtext.append(deleteIpHost.get(2)+"\n");
                outputtext.append(deleteIpHost.get(3)+"\n");outputtext.append(deleteIpHost.get(5)+"\n");outputtext.append(deleteIpHost.get(6)+"\n");
                outputtext.append(deleteIpHost.get(7)+"\n");outputtext.append(deleteIpHost.get(8)+"\n");outputtext.append("  no"+deleteIpHost.get(9)+"\n\n");
                outputtext.append(deleteIpHost.get(10)+"\n");
            }
    
          ArrayList<String> pOut=new ArrayList<>();
         // SMARTG=smartgroup(searchIndex)+A[searchIndex][2]+pVlan.getText(); 
          dataInterface=A[searchIndex][6]+"."+A[searchIndex][2]+pVlan.getText();
          exVlan=A[searchIndex][2];
////          erAip=A[searchIndex][7];
////          erBip=A[searchIndex][8];
            pOut=(pConfig(desc,pSpeed.getText().trim(),pVlan.getText().trim(),A[searchIndex][2],
                    pWanIp.getText().trim(),pLanIp.getText().trim(),pSubnet.getText().trim(),"",dataInterface,"",""));
            
            String finalOut="";
            finalOut= ("                  @T@A@M@I@R@U@     FOR PUBLIC  CUSTOMER     @M@$@L@L@A@  \n\n\n"+
              "    config t\n"+pOut.get(0)+
              "\n   $"+              
              "\n   cgn"+
              "\n    subscriber ipv4 vrf DATA subscriber-id "+A[searchIndex][19]+" nat-domain "+A[searchIndex][20]+
              "\n     interface "+dataInterface)+
              "\n   $"+
              "\n   $";   

            outputtext.append(finalOut);  
            updateTask("!\n"+pOut.get(0)+"\n!");
            save.setEnabled(true);
            save.setBackground(Color.blue);
            save.setForeground(Color.white);
            
        }
      if(!A[searchIndex][6].contains("smartgroup")){
         
           if(deleteIpHost.size()>0){
                outputtext.append(deleteIpHost.get(0)+"\n");outputtext.append(deleteIpHost.get(1)+"\n");outputtext.append(deleteIpHost.get(2)+"\n");
                outputtext.append(deleteIpHost.get(4)+"\n");outputtext.append(deleteIpHost.get(5)+"\n");outputtext.append(deleteIpHost.get(6)+"\n");
                outputtext.append("  no"+deleteIpHost.get(7)+"\n\n");
            }
          ArrayList<String> pOut=new ArrayList<>();
          SMARTG=smartgroup(searchIndex)+A[searchIndex][2]+pVlan.getText(); 
          dataInterface=A[searchIndex][6]+"."+A[searchIndex][2]+pVlan.getText();
          exVlan=A[searchIndex][2];
          erAip=A[searchIndex][7];
          erBip=A[searchIndex][8];
            pOut=(pConfig(desc.trim(),pSpeed.getText().trim(),pVlan.getText().trim(),A[searchIndex][2],
                    pWanIp.getText().trim(),pLanIp.getText().trim(),pSubnet.getText().trim(),SMARTG,dataInterface,erAip,erBip));
            String finalOut="";
            finalOut= ("                  @T@A@M@I@R@U@     FOR PUBLIC --  BRAS --   CUSTOMER     @M@$@L@L@A@  \n\n\n"+
              "    config t\n"+pOut.get(0)+
              "\n                 @T@A@M@I@R@U@     FOR PUBLIC --  ER --  CUSTOMER      @M@$@L@L@A@  \n\n\n\n"+"    config t\n"+pOut.get(1));     

            outputtext.append(finalOut);  
            updateTask("!\n"+pOut.get(0)+"\n!\n"+pOut.get(1)+"\n!");
            save.setEnabled(true);
            save.setBackground(Color.blue);
            save.setForeground(Color.white);

      }
        }
        if(!pWanIp.getText().equals("")){
            String wan=pWanIp.getText(); 
            int temp=subIp(wan,3);
            String wanIp="0.0.0.0";
            if (temp>=64&&temp<=95)wanIp=hostIp(wan,30,0);
            if (temp>=191&&temp<=255) wanIp=hostIp(wan,29,0);
            pWanIp.setText(wanIp); }

        outputtext.setCaretPosition(0);

    }//GEN-LAST:event_pRunActionPerformed

    private void pResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pResetActionPerformed
        pName.setText("");pAccess.setText("");pSpeed.setText("");pVlan.setText("");
        pWanIp.setText("");pLanIp.setText("");pSubnet.setText("");
//        outputtext.setText("");
        deleteIpHost=new ArrayList<>();
        save.setEnabled(false);save.setBackground(Color.white);
        vlanCount=0;ipCount=0;
    }//GEN-LAST:event_pResetActionPerformed

    private void pNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pNameFocusGained
        pName.selectAll();   // TODO add your handling code here:
    }//GEN-LAST:event_pNameFocusGained

    private void pSubnetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pSubnetFocusLost
        // TODO add your handling code here:
        if(!pLanIp.getText().equals(""))
        pLanIp.setText(networkIp(pLanIp.getText(),subNetMask(pSubnet.getText())));
    }//GEN-LAST:event_pSubnetFocusLost

    private void pSubnetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pSubnetFocusGained
        pSubnet.selectAll();  // TODO add your handling code here:
    }//GEN-LAST:event_pSubnetFocusGained

    private void pLanIpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pLanIpFocusLost
        // TODO add your handling code here:

        if(!pSubnet.getText().equals(""))
        pLanIp.setText(networkIp(pLanIp.getText(),subNetMask(pSubnet.getText())));

    }//GEN-LAST:event_pLanIpFocusLost

    private void pLanIpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pLanIpFocusGained
        pLanIp.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_pLanIpFocusGained

    private void pWanIpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pWanIpFocusLost
        // TODO add your handling code here:

        String w=pWanIp.getText();
        int temp=subIp(pWanIp.getText(),3);String wanIp="";int sub=0;int ind=-1;

        if (temp>=64&&temp<=95){
            sub=30;wanIp=hostIp(w,sub,0);
        }
        else if (temp>=191&&temp<=255){
            sub=29;
            wanIp=hostIp(w,sub,0);
        }
        else sub=32;wanIp=hostIp(w,sub,0);
        pWanIp.setText(wanIp);
      ipCount=0;
    }//GEN-LAST:event_pWanIpFocusLost

    private void pWanIpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pWanIpFocusGained
       ipCount=0; pWanIp.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_pWanIpFocusGained

    private void pVlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pVlanFocusGained
        pVlan.selectAll();  // TODO add your handling code here:
        vlanCount=0;
    }//GEN-LAST:event_pVlanFocusGained

    private void pSpeedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pSpeedFocusGained
        pSpeed.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_pSpeedFocusGained

    private void pAccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pAccessActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pAccessActionPerformed

    private void pAccessFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pAccessFocusGained
        pAccess.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_pAccessFocusGained

    private void internetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_internetMouseClicked

    }//GEN-LAST:event_internetMouseClicked

    private void bbNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bbNameFocusGained
        bbName.selectAll();  // TODO add your handling code here:
    }//GEN-LAST:event_bbNameFocusGained

    private void bbAccessFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bbAccessFocusGained
        bbAccess.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_bbAccessFocusGained

    private void bbSpeedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bbSpeedFocusGained
        bbSpeed.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_bbSpeedFocusGained

    private void bbVlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bbVlanFocusGained
        bbVlan.selectAll();  // TODO add your handling code here:
        vlanCount=0;
    }//GEN-LAST:event_bbVlanFocusGained

    private void bbWanIpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bbWanIpFocusGained
        bbWanIp.selectAll(); // TODO add your handling code here:
        ipCount=0;
    }//GEN-LAST:event_bbWanIpFocusGained

    private void bbResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbResetActionPerformed
        // TODO add your handling code here:
        bbName.setText("");bbSpeed.setText("");
        bbVlan.setText("");bbWanIp.setText("");bbAccess.setText("");
//        outputtext.setText("");
        vlanCount=0;
        ipCount=0;
        save.setEnabled(false);save.setBackground(Color.white);
    }//GEN-LAST:event_bbResetActionPerformed

    private void bbRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbRunActionPerformed
        // TODO add your handling code here:
        String desc="";
        if(!(bbName.getText().equals(""))&& !(bbAccess.getText().equals("")))
            desc=(bbAccess.getText().trim()+"_"+bbName.getText()).trim().toUpperCase().replaceAll("\\s++","_")+today();
        else if(bbAccess.getText().equals("")) desc=bbName.getText().trim().toUpperCase().replaceAll("\\s++","_")+today();
        else if(bbName.getText().equals("")) desc=bbAccess.getText().trim().toUpperCase().replaceAll("\\s++","_")+today();
        outputtext.setText("");
        if(searchIndex==-1)outputtext.setText(noMsag());
        else{
            
            
            bbConfig(desc,bbSpeed.getText().trim(),bbVlan.getText().trim(),bbWanIp.getText().trim());
            save.setEnabled(true);
            save.setBackground(Color.blue);
            save.setForeground(Color.white);
           
        }
   vlanCount=0;ipCount=0;
        
    }//GEN-LAST:event_bbRunActionPerformed

    private void msanVlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_msanVlanFocusGained
        // TODO add your handling code here:
        msanVlan.selectAll();
    }//GEN-LAST:event_msanVlanFocusGained

    private void msanPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msanPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msanPortActionPerformed

    private void msanPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_msanPortFocusGained
        msanPort.selectAll();

    }//GEN-LAST:event_msanPortFocusGained

    private void msanResetPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msanResetPortActionPerformed
        // TODO add your handling code here:
        
//       new JFXPanel();
//       Platform.runLater(()->{
//           FileChooser d= new FileChooser();
//           d.setTitle("open");
//           File file=d.showOpenDialog(null);
//           System.out.println(file);
////           d.showOpenDialog(null);
//       });
     
        outputtext.setText("");
        if(searchIndex==-1||A[searchIndex][10].equals("MSAG")||A[searchIndex][10].equals("MDU")){
            String vlan=msanVlan.getText();
            String port=msanPort.getText();
            outputtext.append("\n\n");
            outputtext.append("      vlan del "+vlan);
            outputtext.append("\n"+"      vlan add "+vlan);
            outputtext.append("\n"+"      vlan add-port  "+vlan+"  port  0/9/2  tagged");
            outputtext.append("\n"+"      vlan add-port  "+vlan+"  port  "+port+"  untagged");
            outputtext.append("\n"+"      save");
            outputtext.append("\n");}
        if(A[searchIndex][10].equals("MSAN")){

            String config=String.join("\n"
                ,"     config"
                ,"     service-port  vlan VLAN adsl PORT vpi 8 vci 81 multi-service user-vlan untagged tag-transform add-double inner-vlan INNER");

            String []exp={"VLAN","INNER", "PORT"};
            String []rep={A[searchIndex][2],msanVlan.getText(),msanPort.getText()};
            for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]);

            outputtext.append("\n\n"+config);
        }
    }//GEN-LAST:event_msanResetPortActionPerformed

    private void mnRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRestActionPerformed
        msanVlan.setText("");msanPort.setText("");
//        outputtext.setText("");
    }//GEN-LAST:event_mnRestActionPerformed

    private void eponRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eponRestActionPerformed
        // TODO add your handling code here:
        eponVlan.setText("");eponName.setText("");eponPort.setText(" ");
        eponSpeed.setText("");eponMac.setText("");
//        outputtext.setText("");

    }//GEN-LAST:event_eponRestActionPerformed

    private void eponRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eponRunActionPerformed
        outputtext.setText("");
        String config=String.join("\n"
            ,"    vlan add INNER"
            ,"    vlan add-port INNER port 0/9/2 tagged"
            ,"    vlan add-port INNER port PORT tagged"
            ," "
            ,"    epon onu userinfo PORT userinfo  VLAN-INNER-NAME"
            ," "
            ,"    epon onu auth-mac PORT mac MAC"
            ," "
            ,"    epon  rm-onu manage-cfg PORT enable uninum  1"
            ,"    epon  rm-onu vlan tag PORT uni 1 pri 0 vlan INNER"
            ,"    epon rm-onu port enable PORT uni 1"
            ," "
            ,"    epon dba uplink PORT enable maxbw SPEED minbw SPEED maxburst MAX2S"
            ,"    epon dba downlink PORT enable maxbw SPEED minbw SPEED maxburst MAX2S"
            ," "
            ,"    save");
        String MACIN= eponMac.getText();String m="";String macIn="";
        if (MACIN.length()==12){
            m= MACIN.toLowerCase();
            macIn=(m.substring(0,2)+ ":"+m.substring(2,4)+ ":"+ m.substring(4,6)+":"+ m.substring(6,8) + ":"+ m.substring(8,10) + ":"+ m.substring(10,12));
        }
        if(MACIN.length()==17) macIn=eponMac.getText();
        int SPEED=speed(eponSpeed.getText());
        String Speed=Integer.toString(SPEED);
        ////        int max2s=1000*SPEED;
        ////        int max2sIn;
        ////        if(max2s>8388480) max2sIn=8388480;else max2sIn=max2s;
        ////        String maxBur=Integer.toString(max2sIn);
        String maxBur="150000";
        String eName=eponName.getText().trim().toUpperCase().replaceAll("\\s++","-");
        String []  exp={"INNER","SPEED","MAX2S","PORT","MAC","NAME"};
        String [] rep={eponVlan.getText(),Speed,maxBur,eponPort.getText(),macIn,eName};

        for (int i = 0; i < exp.length; i++) config=config.replace(exp[i],rep[i]);

        outputtext.append("\n\n"+config);
        
    }//GEN-LAST:event_eponRunActionPerformed

    private void eponNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eponNameFocusGained
        eponName.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_eponNameFocusGained

    private void eponMacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eponMacFocusLost
        // TODO add your handling code here:

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(1<=0) {
                } else {
                    String MACIN= eponMac.getText();String m="";
                    String   macIn=MACIN;
                    if (MACIN.length()==12){
                        m= MACIN.toLowerCase();
                        macIn=(m.substring(0,2)+ ":"+m.substring(2,4)+ ":"+ m.substring(4,6)+":"+ m.substring(6,8) + ":"+ m.substring(8,10) + ":"+ m.substring(10,12));
                    } if (MACIN.length()==17) macIn=eponMac.getText();
                    eponMac.setText(macIn);

                }  }
            });
    }//GEN-LAST:event_eponMacFocusLost

    private void eponMacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eponMacFocusGained
        eponMac.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_eponMacFocusGained

    private void eponPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eponPortFocusGained
        eponPort.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_eponPortFocusGained

    private void eponVlanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eponVlanFocusGained
        eponVlan.selectAll(); // TODO add your handling code here:
    }//GEN-LAST:event_eponVlanFocusGained

    private void eponSpeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eponSpeedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eponSpeedActionPerformed

    private void eponSpeedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eponSpeedFocusGained
        eponSpeed.selectAll();   // TODO add your handling code here:
    }//GEN-LAST:event_eponSpeedFocusGained

    private void bbVlanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bbVlanFocusLost
        // TODO add your handling code here:
        vlanCount=0;
        bbVlan.selectAll();
    }//GEN-LAST:event_bbVlanFocusLost

    private void pVlanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pVlanFocusLost
        // TODO add your handling code here:
        vlanCount=0;
    }//GEN-LAST:event_pVlanFocusLost

    private void vpnVlanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vpnVlanFocusLost
        vlanCount=0;        // TODO add your handling code here:
    }//GEN-LAST:event_vpnVlanFocusLost

    private void bbWanIpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bbWanIpFocusLost
     ipCount=0;   // TODO add your handling code here:
    }//GEN-LAST:event_bbWanIpFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         if(searchIndex>=0) {
             if(vlanCount==0)freeVlanData=takenVlanFinder(iphostDataA);
        ArrayList<String> Data=new ArrayList<>();
        Data=iphostDataA;            
       //ArrayList<String> data=freeVlanFinder(Data,A[searchIndex][2]);     
        ArrayList<String> data=freeVlanFinder(); 
       String vlanStart=vlanStart =bbVlan.getText();
       if(vlanStart.trim().equals(""))vlanStart="1001";
     if (vlanCount==0) {

        for (int i = 0; i < data.size(); i++) {
            if(str2num(data.get(i))>=str2num(vlanStart)){
                vlanCount=i;
                break;
            }           
        }
     }
      
       bbVlan.setText(data.get(vlanCount));       
       vlanCount++;
       
      }else{ 
          outputtext.setText("");
          outputtext.append("\n\n\n\n\n                 <<<                                                 "
           + "                                              Select  MSAG  First ....    !!!!"
           + "                                                          >>>"); 
      }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         if(searchIndex>=0) {
    String ipGrp=A[searchIndex][17];
    int index=findIndex(dataColls(IPrange,0),ipGrp);
    
//    ArrayList <String> FIP100=freeIpFinder(IPrange[index][1]);
//    ArrayList <String> FIP200=freeIpFinder(IPrange[index][2]);
//    ArrayList <String> FIP1700=freeIpFinder(IPrange[index][3]);  
            
    ArrayList <String> FIP100=freeIpFinder(index,findIndex(IPrange[0],"vbui100 broadband"));
    ArrayList <String> FIP101=freeIpFinder(index,findIndex(IPrange[0],"vbui101 broadband"));
    ArrayList <String> FIP200=freeIpFinder(index,findIndex(IPrange[0],"vbui200 broadband"));
    ArrayList <String> FIP300=freeIpFinder(index,findIndex(IPrange[0],"vbui300 broadband"));
    ArrayList <String> FIP1700=freeIpFinder(index,findIndex(IPrange[0],"vbui1700 broadband")); 
    
    ArrayList <String> data=new ArrayList <>();
    for(String x:FIP100) data.add(x);
    for(String x1:FIP101) data.add(x1);
    for(String x3:FIP300) data.add(x3);
    for(String y:FIP200) data.add(y);
    for(String z:FIP1700) data.add(z);
    
 String ipStart =bbWanIp.getText();
  if(ipStart.trim().equals(""))ipStart=data.get(0);    
 if (ipCount==0) {
        for (int i = 0; i < data.size(); i++) {
            if( Integer.parseInt(subIp(data.get(i),1)+subIp(data.get(i),2)+subIp(data.get(i),3)+"")
                     >=Integer.parseInt(subIp(ipStart,1)+subIp(ipStart,2)+subIp(ipStart,3)+""))
            {
                ipCount=i;
                break;
            }           
        }
     }      
       bbWanIp.setText(data.get(ipCount));       
       ipCount++;
  }else{ 
       outputtext.setText("");
          outputtext.append("\n\n\n\n\n                 <<<                                                 "
           + "                                              Select  MSAG  First ....    !!!!"
           + "                                                          >>>");
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         if(searchIndex>=0) {   
          if(vlanCount==0)freeVlanData=takenVlanFinder(iphostDataA);
        ArrayList<String> Data=new ArrayList<>();
        Data=iphostDataA;       
    //ArrayList<String> data=freeVlanFinder(Data,A[searchIndex][2]);   
         ArrayList<String> data=freeVlanFinder();  
       String vlanStart=vlanStart =pVlan.getText();
       if(vlanStart.trim().equals(""))vlanStart="1001";
     if (vlanCount==0) {
        for (int i = 0; i < data.size(); i++) {
            if(str2num(data.get(i))>=str2num(vlanStart)){
                vlanCount=i;
                break;
            }           
        }
     }      
       pVlan.setText(data.get(vlanCount));       
       vlanCount++;
    }else{ 
         outputtext.setText("");
          outputtext.append("\n\n\n\n\n                 <<<                                                 "
           + "                                              Select  MSAG  First ....    !!!!"
           + "                                                          >>>");
       }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
   if(searchIndex>=0) {  
    String ipGrp=A[searchIndex][17];
   int index=findIndex(dataColls(IPrange,0),ipGrp);
    ArrayList <String> data=freeIpFinder(index,findIndex(IPrange[0],"Public IP Range"));
 //for(String S:data)System.out.println(S);
 String ipStart=ipStart =pWanIp.getText();
  if(ipStart.trim().equals(""))ipStart=data.get(0);    
      
 if (ipCount==0) {

        for (int i = 0; i < data.size(); i++) {
           
            //if(data.get(i).contains(subIp(ipStart,1)+"."+subIp(ipStart,2)+"."+subIp(ipStart,3))){
             if( Integer.parseInt(subIp(data.get(i),1)+subIp(data.get(i),2)+subIp(data.get(i),3)+"")
                     >=Integer.parseInt(subIp(ipStart,1)+subIp(ipStart,2)+subIp(ipStart,3)+"")){
               ipCount=i;
               break;
            }           
        }
     }
      
       pWanIp.setText(data.get(ipCount));       
       ipCount++;
    }else{ 
         outputtext.setText("");
          outputtext.append("\n\n\n\n\n                 <<<                                                 "
           + "                                              Select  MSAG  First ....    !!!!"
           + "                                                          >>>");
       }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(searchIndex>=0){
  String ipGrp=A[searchIndex][17];
  int index=findIndex(dataColls(IPrange,0),ipGrp);
  ArrayList <String> data=freeIpFinder(index,findIndex(IPrange[0],"VPN IP Range"));
 String ipStart=ipStart =vpnWanIp.getText();
  if(ipStart.trim().equals(""))ipStart=data.get(0);    
 if (ipCount==0) {

        for (int i = 0; i < data.size(); i++) {
            if( Integer.parseInt(subIp(data.get(i),1)+subIp(data.get(i),2)+subIp(data.get(i),3)+"")
                     >=Integer.parseInt(subIp(ipStart,1)+subIp(ipStart,2)+subIp(ipStart,3)+"")){
                ipCount=i;
                break;
            }           
        }
     }
      
       vpnWanIp.setText(data.get(ipCount));       
       ipCount++;
    }else{ 
           outputtext.setText("");
          outputtext.append("\n\n\n\n\n                 <<<                                                 "
           + "                                              Select  MSAG  First ....    !!!!"
           + "                                                          >>>");
       }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
     if(searchIndex>=0){
          if(vlanCount==0)freeVlanData=takenVlanFinder(iphostDataA);
        ArrayList<String> Data=new ArrayList<>();
       Data=iphostDataA;       
//       ArrayList<String> data=freeVlanFinder(Data,A[searchIndex][2]);    
       ArrayList<String> data=freeVlanFinder(); 
       String vlanStart =vpnVlan.getText();
       if(vlanStart.trim().equals(""))vlanStart="1001";
     if (vlanCount==0) {

        for (int i = 0; i < data.size(); i++) {
            if(str2num(data.get(i))>=str2num(vlanStart)){
                vlanCount=i;
                break;
            }           
        }
     }
      
       vpnVlan.setText(data.get(vlanCount));       
       vlanCount++;
     }else{ 
    outputtext.setText("");
          outputtext.append("\n\n\n\n\n                 <<<                                                 "
           + "                                              Select  MSAG  First ....    !!!!"
           + "                                                          >>>");
       }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void iImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iImportActionPerformed
       
        if((!(outputtext.getText().equals("")))&&(outputtext.getText().contains("ip host"))&&
                (outputtext.getText().contains("vlan"))&&(outputtext.getText().contains("up-rate"))){
            String[] A=outputtext.getText().trim().split("\n"); String ff="";String selected="";
            selected=outputtext.getSelectedText();
            for (int i = 0; i < A.length; i++) {
                if(A[i].contains("description"))ff=A[i].trim();
            }

            String nameAccess=ff.trim().split("\\s+")[(ff.trim().split("\\s+").length)-1];
            if(!(selected==null)){
                bbName.setText((nameAccess.replace(selected,"").replace("_"," ").replace("-"," ")).trim());
                bbAccess.setText(selected);
            }else{
                bbAccess.setText(accessNameFinder(nameAccess).get(0));
                bbName.setText(accessNameFinder(nameAccess).get(1));
            }
            if(outputtext.getText().contains("second-vlan")){
                vlan.setText(midString("vlan","second-vlan"));
                bbVlan.setText(midString("second-vlan","up-rate"));
            }else{
                vlan.setText(midString("vlan","up-rate"));
                bbVlan.setText("blocked");
            }

            if(midString("up-rate","down-rate").equals("66")|| midString("up-rate","down-rate").equals("512")|| midString("up-rate","down-rate").equals("256"))
            bbSpeed.setText(midString("up-rate","down-rate"));
            else  bbSpeed.setText(""+Integer.parseInt(midString("up-rate","down-rate"))/1024);

            if(outputtext.getText().contains("slot")&&outputtext.getText().contains("port"))
            bbWanIp.setText(midString("ip host","slot"));
            else
            bbWanIp.setText(midString("ip host","smartgroup"));
            activity="Line Shift";
            searchIndex=-1;
            msag.setText("");msagIp.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");

        }
     
        if((!(outputtext.getText().equals("")))&&(outputtext.getText().contains("ip-host"))&&
                (outputtext.getText().contains("vlan"))&&(outputtext.getText().contains("author-temp-name"))){
            String[] A=outputtext.getText().trim().split("\n"); String ff="";String selected="";
            selected=outputtext.getSelectedText();
            for (int i = 0; i < A.length; i++) {
                if(A[i].contains("description"))ff=A[i].trim();
            }

            String nameAccess=ff.trim().split("\\s+")[2];
            if(!(selected==null)){
                bbName.setText((nameAccess.replace(selected,"").replace("_"," ").replace("-"," ")).trim());
                bbAccess.setText(selected);
            }else{
                bbAccess.setText(accessNameFinder(nameAccess).get(0));
                bbName.setText(accessNameFinder(nameAccess).get(1));
            }
            if(outputtext.getText().contains("sec-vlan")){
                vlan.setText(midString("vlan","sec-vlan"));
                bbVlan.setText(midString("sec-vlan","author-temp-name"));
            }else{
                vlan.setText(midString("vlan","author-temp-name"));
                bbVlan.setText("blocked");
            }
            
        if(outputtext.getText().contains("author-temp-name")) 
            bbSpeed.setText(speedNr(ff.trim().split("\\s+")[(ff.trim().split("\\s+").length)-1]));
        
////            if(midString("up-rate","down-rate").equals("66")|| midString("up-rate","down-rate").equals("512")|| midString("up-rate","down-rate").equals("256"))
////            bbSpeed.setText(midString("up-rate","down-rate"));
////            else  
               //bbSpeed.setText(""+Integer.parseInt(midString("up-rate","down-rate"))/1024);

            if(outputtext.getText().contains("slot")&&outputtext.getText().contains("port"))
            bbWanIp.setText(midString("ip host","slot"));
            else
            bbWanIp.setText(ff.trim().split("\\s+")[3]);
            activity="Line Shift";
            searchIndex=-1;
            msag.setText("");msagIp.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");

        }
    }//GEN-LAST:event_iImportActionPerformed

    private void pImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pImportActionPerformed
          if(((outputtext.getText().contains("ip address"))||(outputtext.getText().contains("ip-host"))||
                  (outputtext.getText().contains("ip host")))&&(!outputtext.getText().contains("ip vrf forwarding"))){

            String[] A=outputtext.getText().trim().split("\n"); 
            String ff="";String gg="";String selected="";String nameAccess,vrf="";
            for (int i = 0; i < A.length; i++) {
                if(A[i].contains("description"))ff=A[i].trim();
            }
            if(outputtext.getText().contains("ip-host")){
               
                nameAccess=ff.trim().split("\\s+")[2];
                selected=outputtext.getSelectedText();
                if(!(selected==null)){
                    pName.setText((nameAccess.replace(selected,"").replace("_"," ").replace("-"," ")).trim());
                    pAccess.setText(selected);
                }else if (selected==null){
                    pAccess.setText(accessNameFinder(nameAccess).get(0));
                    pName.setText(accessNameFinder(nameAccess).get(1));
                }
                pVlan.setText(midString("sec-vlan","author-temp-name"));
                deleteIpHost=changeVlan(outputtext.getText(),midString("sec-vlan","author-temp-name"));
//                if(midString("up-rate","down-rate").equals("66")|| midString("up-rate","down-rate").equals("512")|| midString("up-rate","down-rate").equals("256"))
//                pSpeed.setText(midString("up-rate","down-rate"));
//                else  pSpeed.setText(""+Integer.parseInt(midString("up-rate","down-rate"))/1024);
               if(outputtext.getText().contains("author-temp-name")) 
               pSpeed.setText(speedNr(ff.trim().split("\\s+")[(ff.trim().split("\\s+").length)-1]));
               vlan.setText(midString("vlan","sec-vlan"));
                pWanIp.setText("");
            }
            
           if(outputtext.getText().contains("ip host")){
               
                nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
                selected=outputtext.getSelectedText();
                if(!(selected==null)){
                    pName.setText((nameAccess.replace(selected,"").replace("_"," ").replace("-"," ")).trim());
                    pAccess.setText(selected);
                }else if (selected==null){
                    pAccess.setText(accessNameFinder(nameAccess).get(0));
                    pName.setText(accessNameFinder(nameAccess).get(1));
                }
                pVlan.setText(midString("second-vlan","up-rate"));
                deleteIpHost=changeVlan(outputtext.getText(),midString("second-vlan","up-rate"));
                if(midString("up-rate","down-rate").equals("66")|| midString("up-rate","down-rate").equals("512")|| midString("up-rate","down-rate").equals("256"))
                pSpeed.setText(midString("up-rate","down-rate"));
                else  pSpeed.setText(""+Integer.parseInt(midString("up-rate","down-rate"))/1024);

              vlan.setText(midString("vlan","second-vlan"));
                pWanIp.setText("");
            }
           
           else if(!(outputtext.getText().contains("ip-host")||outputtext.getText().contains("ip host"))){
                nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
                selected=outputtext.getSelectedText();
                if(!(selected==null)){
                    pName.setText(nameAccess.replace(selected,"").replace("-"," ").replace("_"," "));
                    pAccess.setText(selected);
                }else{
                    pAccess.setText(accessNameFinder(nameAccess).get(0));
                    pName.setText(accessNameFinder(nameAccess).get(1));
                }
                String inVlan=midString("internal-vlan","external-vlan");
                for (int i = 0; i < A.length; i++) if(A[i].contains("external-vlan"))gg=A[i].trim();

                String exVlan=gg.substring(gg.trim().indexOf("external-vlan")+13,gg.length()).trim();
                pVlan.setText(inVlan.split("[a-zA-Z]+")[inVlan.split("[a-zA-Z]+").length-1].trim());
                if(midString("cir","cbs").equals("64")|| midString("cir","cbs").equals("512")|| midString("cir","cbs").equals("256"))
                pSpeed.setText(midString("cir","cbs"));else  pSpeed.setText(""+Integer.parseInt(midString("cir","cbs"))/1024);
                String wan=midString("ip address","255"); int temp=subIp(wan,3);String wanIp="0.0.0.0";
                if (temp>=64&&temp<=95)wanIp=hostIp(wan,30,0);
                if (temp>=191&&temp<=255) wanIp=hostIp(wan,29,0);
                pWanIp.setText(wanIp);

                vlan.setText(exVlan.split("\\s+")[exVlan.split("\\s+").length-1]);
            }
           
           
           
            
            activity="Line Shift";
            msag.setText("");msagIp.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
            searchIndex=-1;

        }
    if((((outputtext.getText().contains("ip address"))))&&
            (outputtext.getText().contains("ip vrf forwarding"))){
//            String[] A =null;
             String[] A=outputtext.getText().trim().split("\n"); String ff="";String gg="";String vrf="";String selected=""; String nameAccess;
            for (int i = 0; i < A.length; i++) {
                if(A[i].contains("description"))ff=A[i].trim();                
                if(A[i].contains("external-vlanid"))gg=A[i].trim(); 
                if(A[i].contains("ip vrf forwarding"))vrf=A[i].trim();
            }
           
            String exVlan=gg.substring(gg.trim().indexOf("external-vlanid")+15,gg.length()).trim();
            String Vrf=vrf.substring(vrf.trim().indexOf("ip vrf forwadrding")+17,vrf.length()).trim();
            vlan.setText(exVlan.split("\\s+")[exVlan.split("\\s+").length-1]);

            selected=outputtext.getSelectedText();
            nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
        
            if(!(selected==null)){
                pName.setText((nameAccess.replace(selected,"").replace("_"," ").replace("-"," ")).trim());
                pAccess.setText(selected.trim());
            }else if (selected==null){
                pAccess.setText(accessNameFinder(nameAccess).get(0));
                pName.setText(accessNameFinder(nameAccess).get(1));
            }
           if(outputtext.getText().contains("kbps cbs ")&&outputtext.getText().contains("cir")){
               
               
            if(midString("cir","kbps").equals("66")|| midString("cir","kbps").equals("512")|| midString("cir","kbps").equals("256"))
               pSpeed.setText(midString("cir","kbps"));
            else  
               pSpeed.setText(""+Integer.parseInt(midString("cir","kbps"))/1024);
            
            }
            if(!outputtext.getText().contains("kbps cbs")&&outputtext.getText().contains("cir")){
            if(midString("cir","cbs").equals("66")|| midString("cir","cbs").equals("512")|| midString("cir","cbs").equals("256"))
               pSpeed.setText(midString("cir","cbs"));
            else  
               pSpeed.setText(""+Integer.parseInt(midString("cir","cbs"))/1024);
            }
            String inVlan=midString("internal-vlanid","external-vlanid");
            pVlan.setText(inVlan.split("[a-zA-Z]+")[inVlan.split("[a-zA-Z]+").length-1].trim());
            pWanIp.setText(networkIp(midString("ip address","255"),29));
            //vpnVrf.setText(Vrf.split("\\s+")[Vrf.split("\\s+").length-1]);
         //if(outputtext.getText().contains("vrrp")&&outputtext.getText().contains("accept")) vpnVrrp.setText(midString("vrrp","accept"));
            activity="Line Shift";
            msag.setText("");msagIp.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
            searchIndex=-1;
        }
////    if((((outputtext.getText().contains("ip address")))||(!(outputtext.getText().contains("interface smartgroup"))))&&
////            (outputtext.getText().contains("ip vrf forwarding"))){
//////            String[] A =null;
////             String[] A=outputtext.getText().trim().split("\n"); String ff="";String gg="";String vrf="";String selected=""; String nameAccess;
////            for (int i = 0; i < A.length; i++) {
////                if(A[i].contains("description"))ff=A[i].trim();                
////                if(A[i].contains("external-vlanid"))gg=A[i].trim(); 
////                if(A[i].contains("ip vrf forwarding"))vrf=A[i].trim();
////            }
////           
////            String exVlan=gg.substring(gg.trim().indexOf("external-vlanid")+15,gg.length()).trim();
////            String Vrf=vrf.substring(vrf.trim().indexOf("ip vrf forwadrding")+17,vrf.length()).trim();
////            vlan.setText(exVlan.split("\\s+")[exVlan.split("\\s+").length-1]);
////
////            selected=outputtext.getSelectedText();
////            nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
////        
////            if(!(selected==null)){
////                pName.setText((nameAccess.replace(selected,"").replace("_"," ").replace("-"," ")).trim());
////                pAccess.setText(selected.trim());
////            }else if (selected==null){
////                pAccess.setText(accessNameFinder(nameAccess).get(0));
////                pName.setText(accessNameFinder(nameAccess).get(1));
////            }
////            if(midString("cir","cbs").equals("66")|| midString("cir","cbs").equals("512")|| midString("cir","cbs").equals("256"))
////            pSpeed.setText(midString("cir","cbs"));else  pSpeed.setText(""+Integer.parseInt(midString("cir","cbs"))/1024);
////            String inVlan=midString("internal-vlanid","external-vlanid");
////            pVlan.setText(inVlan.split("[a-zA-Z]+")[inVlan.split("[a-zA-Z]+").length-1].trim());
////            pWanIp.setText(networkIp(midString("ip address","255"),29));
////            //vpnVrf.setText(Vrf.split("\\s+")[Vrf.split("\\s+").length-1]);
////         //if(outputtext.getText().contains("vrrp")&&outputtext.getText().contains("accept")) vpnVrrp.setText(midString("vrrp","accept"));
////            activity="Line Shift";
////            msag.setText("");msagIp.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
////            searchIndex=-1;
////        }
////    
    

    }//GEN-LAST:event_pImportActionPerformed

    private void vpnImnortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vpnImnortActionPerformed
              if(((outputtext.getText().contains("ip address")))||(outputtext.getText().contains("ip vrf forwarding"))){
//            String[] A =null;
             String[] A=outputtext.getText().trim().split("\n"); String ff="";String gg="";String vrf="";String selected=""; String nameAccess;
            for (int i = 0; i < A.length; i++) {
                if(A[i].contains("description"))ff=A[i].trim();                
                if(A[i].contains("external-vlanid"))gg=A[i].trim(); 
                if(A[i].contains("ip vrf forwarding"))vrf=A[i].trim();
            }
           
            String exVlan=gg.substring(gg.trim().indexOf("external-vlanid")+15,gg.length()).trim();
            String Vrf=vrf.substring(vrf.trim().indexOf("ip vrf forwadrding")+17,vrf.length()).trim();
            vlan.setText(exVlan.split("\\s+")[exVlan.split("\\s+").length-1]);

            selected=outputtext.getSelectedText();
            nameAccess=ff.substring(ff.trim().indexOf("description")+11,ff.length()).trim();
        
            if(!(selected==null)){
                vpnName.setText((nameAccess.replace(selected,"").replace("_"," ").replace("-"," ")).trim());
                vpnAccess.setText(selected.trim());
            }else if (selected==null){
                vpnAccess.setText(accessNameFinder(nameAccess).get(0));
                vpnName.setText(accessNameFinder(nameAccess).get(1));
            }
            if(midString("cir","kbps").equals("66")|| midString("cir","kbps").equals("512")|| midString("cir","kbps").equals("256"))
            vpnSpeed.setText(midString("cir","kbps"));else  vpnSpeed.setText(""+Integer.parseInt(midString("cir","kbps"))/1024);
            String inVlan=midString("internal-vlanid","external-vlanid");
            vpnVlan.setText(inVlan.split("[a-zA-Z]+")[inVlan.split("[a-zA-Z]+").length-1].trim());
            vpnWanIp.setText(networkIp(midString("ip address","255"),29));
            vpnVrf.setText(Vrf.split("\\s+")[Vrf.split("\\s+").length-1]);
         if(outputtext.getText().contains("vrrp")&&outputtext.getText().contains("accept")) vpnVrrp.setText(midString("vrrp","accept"));
            activity="Line Shift";
            msag.setText("");msagIp.setText("");msagTag.setText("");vlanTag.setText("");area.setText("");
            searchIndex=-1;
        }
    }//GEN-LAST:event_vpnImnortActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpActionPerformed
        outputtext.append(" HELP :\n\n\n");
        for(String S:helpData)outputtext.append(S+"\n");
        outputtext.setCaretPosition(0);
    }//GEN-LAST:event_helpActionPerformed

    private void cutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutActionPerformed
        String x=outputtext.getSelectedText();
        StringSelection dat =new StringSelection (x);
        clip.setContents(dat,dat);
        outputtext.replaceRange("",outputtext.getSelectionStart(),outputtext.getSelectionEnd());
    }//GEN-LAST:event_cutActionPerformed

    private void pasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteActionPerformed
        java.awt.datatransfer.Transferable clipdata=clip.getContents(clip);
        try{
            if(clipdata.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String s=(String)(clip.getContents(clip).getTransferData(DataFlavor.stringFlavor));
                outputtext.replaceSelection(s);
            }
        }catch (Exception e){}
    }//GEN-LAST:event_pasteActionPerformed

    private void copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyActionPerformed
        String x=outputtext.getSelectedText();
        StringSelection dat =new StringSelection (x);
        clip.setContents(dat,dat);
    }//GEN-LAST:event_copyActionPerformed

    private void jMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu5ActionPerformed

    private void resetReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetReportActionPerformed
        JPasswordField pf=new JPasswordField();String pass="";
        int status=JOptionPane.showConfirmDialog(null, pf,"Password : ",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(status==JOptionPane.OK_OPTION) pass=new String(pf.getPassword());
        if(pass.equals("tame@123")) {
            copySrcDest("reset/Report.xml","Report.xml");
            outputtext.setText("");
            outputtext.append("\n\n\n");
            outputtext.append("\t\t\t Daily Report is Reseted");
        }else {
            outputtext.setText("");
            outputtext.append("\n\n\n");
            outputtext.append("\t\t\t Incorrect Password");
        }
    }//GEN-LAST:event_resetReportActionPerformed

    private void resetOverAllReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetOverAllReportActionPerformed
        JPasswordField pf=new JPasswordField();String pass="";
        int status=JOptionPane.showConfirmDialog(null, pf,"Password : ",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(status==JOptionPane.OK_OPTION) pass=new String(pf.getPassword());
        if(pass.equals("tame@123")) {
            copySrcDest("reset/BackUp.xml","BackUp.xml");
            outputtext.setText("");
            outputtext.append("\n\n\n");
            outputtext.append("\t\t\t Over All Report is Reseted");
        }else {
            outputtext.setText("");
            outputtext.append("\n\n\n");
            outputtext.append("\t\t\t Incorrect Password");
        }
    }//GEN-LAST:event_resetOverAllReportActionPerformed

    private void overAllReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overAllReportActionPerformed
        new JFXPanel();
        Platform.runLater(()->{
            outputtext.setText("");            
            ArrayList<ArrayList<String>> backup=xmlArrayRead("BackUp");
            backup=emptyRemover(backup);
            xmlArrayWrite(backup,"BackUp");
            FileChooser fc=new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File","*.xls"));
            fc.setTitle("Save As");
            File file=fc.showSaveDialog(null);
            String path =(""+file+"_"+cDate).replace(".xls","");
            xlWrite("BackUp",path);
        });
    }//GEN-LAST:event_overAllReportActionPerformed

    private void dailyReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyReportActionPerformed

        new JFXPanel();
        Platform.runLater(()->{
            outputtext.setText("");
            ArrayList<ArrayList<String>> report=xmlArrayRead("Report");
            report=emptyRemover(report);
            xmlArrayWrite(report,"Report");
            FileChooser fc=new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File","*.xls"));
            fc.setTitle("Save As");
            File file=fc.showSaveDialog(null);
            String path =(""+file+"_"+cDate).replace(".xls",""); ;
            xlWrite("Report",path);
        });
    }//GEN-LAST:event_dailyReportActionPerformed

    private void backupBRERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupBRERActionPerformed
        outputtext.setText("");
        iphostData=new ArrayList<>();
        ArrayList<String>  allData = new ArrayList<>();
        new JFXPanel();
        Platform.runLater(()->{
            FileChooser fc=new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File","*.txt"));
            fc.setTitle("Open");
            File file=fc.showOpenDialog(null);
            if(file!=null){
                copySrcDest(""+file,"DATA.txt");
                try {
                    Scanner inn =new Scanner(file);
                    while (inn.hasNextLine())
                    {allData.add(inn.nextLine());}
                } catch (Exception ex) {}
                allData.removeAll(Arrays.asList("", null));
                ArrayList <Integer> indiBig= searchIndexor(allData,"ip-host","ip host");
                ArrayList <String>iphostData1 = new ArrayList<>();
                String b="";
                boolean c;
                String B;
                for(int p=((indiBig.size())-1);p>=0;p--)
                {
                    B=allData.get((indiBig.get(p))+1);
                    c=B.contains("ip-host")||B.contains("ip host");
                    if (c==true) {
                        b="";
                        iphostData1.add(allData.get(indiBig.get(p))+b);
                    }
                    if(c==false){
                        b=allData.get((indiBig.get(p))+1);
                        iphostData1.add(allData.get(indiBig.get(p))+b);
                    }
                }
                ArrayList <Integer> indiBig2= searchIndexor(allData,"ip-host","ip host");
                for(int p=((indiBig2.size())-1);p>=0;p--){
                    if(!(allData.get((indiBig2.get(p))+1).contains("ip-host")||allData.get((indiBig2.get(p))+1).contains("ip host")))
                    allData.remove((indiBig2.get(p))+1);}
                ArrayList <String> newAllData= new ArrayList<>();
                for(String S:allData)
                if(!(S.contains("ip-host")||S.contains("ip host"))) newAllData.add(S);
                iphostData1.removeAll(Arrays.asList("", null));
                newAllData.removeAll(Arrays.asList("", null));
                newAllData.forEach((V) -> {
                    iphostData.add(V);

                });
                for (int i = 0; i < iphostData1.size(); i++) {
                    int m=iphostData1.size()-1;
                    iphostData.add(iphostData1.get(m-i));

                }
                iphostDataA=new ArrayList<>();
                iphostDataA=new ArrayList(iphostData);
                
           for (int i = 1; i < IPrange.length; i++) {
             for (int j = 1; j <IPrange[i].length-1 ; j++) {
            allIpData[i][j]=allIpFinder (IPrange[i][j]); 
            takenIpData[i][j]=takenIpFinder(iphostDataA,IPrange[i][j]);
          
         }
         
     }
        for (int i = 1; i < IPrange.length; i++) {
         for (int j = 1; j <IPrange[i].length-1 ; j++) {
            freeIpData[i][j] =XOR ( allIpData[i][j],takenIpData[i][j]); 
      
         }
         
     }        
                
                for(String mike:xmlListRead("updatedTask"))iphostDataA.add(mike);
                 for(String mike:xmlListRead("updatedTask"))updatedData.add(mike); 
                outputtext.setText("\n\n\n\n\n                 <<<                                                 "
                    + "                                              Backup  File   is  Loaded  Successfully    !!!!"
                    + "                                                         >>>");
                
//      if(!(xmlStringRead("CDATE").equals((""+cDate).trim()))){        
//        copySrcDest("reset/updatedTask.xml","updatedTask.xml");      
//       }

            }
        });
    }//GEN-LAST:event_backupBRERActionPerformed

    private void databaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseActionPerformed

        new JFXPanel();
        Platform.runLater(()->{
            outputtext.setText("");
            FileChooser fc=new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File","*.xls"));
            fc.setTitle("Open");
            File filee=fc.showOpenDialog(null);
            if(filee!=null){
                String excelFilePath = ""+filee;
                copySrcDest(excelFilePath,"data.xls");
                A=xlRead(excelFilePath,"database");
                oltTt=xlRead(excelFilePath,"olt-tt");
                IPrange=xlRead(excelFilePath,"ip-group-range");
                ipAllocationData=xlRead(excelFilePath,"ipAllocation");
                brData=xlRead(excelFilePath,"brData");
                erData=xlRead(excelFilePath,"erData");
                swData=xlRead(excelFilePath,"swData");

                outputtext.setText("\n\n\n\n\n                 <<<                                                 "
                    + "                                               Database  File   is  Loaded  Successfully    !!!!"
                    + "                                                          >>>");
            }
        });
    }//GEN-LAST:event_databaseActionPerformed

    private void rSearchOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSearchOKActionPerformed
   
  
     
        
   ArrayList<ArrayList<String>> backup=xmlArrayRead("BackUp"); 
   backup=emptyRemover(backup); 
   
           ArrayList<Integer> sInd= new ArrayList<>();
           ArrayList<Integer> sInd1= new ArrayList<>();
           ArrayList<Integer> sInd2= new ArrayList<>();
           ArrayList<Integer> sInd3= new ArrayList<>();

outputtext.setText("");
if((searchIndex(dataColls(backup,0),String.valueOf(rSearch.getText().toLowerCase().trim())).size())>=1)
    sInd1=searchIndex(dataColls(backup,0),String.valueOf(rSearch.getText().toLowerCase().trim()));
if((searchIndex(dataColls(backup,2),String.valueOf(rSearch.getText()).toLowerCase().trim()).size())>=1)
    sInd2=searchIndex(dataColls(backup,2),String.valueOf(rSearch.getText().toLowerCase().trim()));
if((searchIndex(dataColls(backup,8),String.valueOf(rSearch.getText().toLowerCase().trim())).size())>=1)
    sInd3=searchIndex(dataColls(backup,8),String.valueOf(rSearch.getText().toLowerCase().trim()));
for(int A:sInd1)sInd.add(A);
for(int A:sInd2)sInd.add(A);
for(int A:sInd3)sInd.add(A);
rSrchRemark.setText("  "+sInd.size());
if(sInd.size()==1)rSrchCount=0;       
if(sInd.size()>0)rSrchIndex=sInd.get(rSrchCount);
int i=rSrchIndex;


if(rSearch.getText().isEmpty()){
       for (int j = 1; j < backup.size(); j++) {         
outputtext.append(" \n\n"+j+" ) ------------------------------------------");
outputtext.append("\n\n"+"  Customer Name  =  "+backup.get(j).get(0));
outputtext.append("\n\n"+"   Access Number  =  "+backup.get(j).get(2));
outputtext.append("\n\n"+"               Wan IP  =  "+backup.get(j).get(8));
outputtext.append("\n\n"+"                    Vlan  =  "+backup.get(j).get(9));
outputtext.append("\n\n"+"                  Msag  =  "+backup.get(j).get(6));
outputtext.append("\n\n"+"                Speed  =  "+backup.get(j).get(7));
outputtext.append("\n\n"+"                 Event  =  "+backup.get(j).get(1));
outputtext.append("\n\n"+"                  Date  =  "+backup.get(j).get(5));
outputtext.append("\n\n"+"                  Area  =  "+backup.get(j).get(3));
outputtext.append("\n\n"+"     Product Type  =  "+backup.get(j).get(4));


    } 
  rSrchIndex=-1;
  outputtext.setCaretPosition(0);
    }

if(sInd.size()>0&&rSrchIndex>-1){
    outputtext.append("  \n"+i+" ) ------------------------------------------");
outputtext.append("\n\n"+"  Customer Name  =  "+backup.get(i).get(0));
outputtext.append("\n\n"+"   Access Number  =  "+backup.get(i).get(2));
outputtext.append("\n\n"+"               Wan IP  =  "+backup.get(i).get(8));
outputtext.append("\n\n"+"                    Vlan  =  "+backup.get(i).get(9));
outputtext.append("\n\n"+"                  Msag  =  "+backup.get(i).get(6));
outputtext.append("\n\n"+"                Speed  =  "+backup.get(i).get(7));
outputtext.append("\n\n"+"                 Event  =  "+backup.get(i).get(1));
outputtext.append("\n\n"+"                  Date  =  "+backup.get(i).get(5));
outputtext.append("\n\n"+"                  Area  =  "+backup.get(i).get(3));
outputtext.append("\n\n"+"     Product Type  =  "+backup.get(i).get(4));
      
        
    rSrchCount++;
  if(rSrchCount>=sInd.size())rSrchCount=0;    
  } 
    }//GEN-LAST:event_rSearchOKActionPerformed

    private void rSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rSearchFocusGained
       rSrchCount=0;
       rSrchIndex=-1;
       rSearch.selectAll();
       rSrchRemark.setText("");
    }//GEN-LAST:event_rSearchFocusGained

    private void edit_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_deleteActionPerformed
   
    ArrayList<ArrayList<String>> backup=xmlArrayRead("BackUp");
    ArrayList<ArrayList<String>> report=xmlArrayRead("Report");
    int b=-1;
        for (int i = 0; i < report.size(); i++) {
         if((report.get(i).get(0).equalsIgnoreCase(backup.get(rSrchIndex).get(0)))&&
             (report.get(i).get(1).equalsIgnoreCase(backup.get(rSrchIndex).get(1)))&&
             (report.get(i).get(2).equalsIgnoreCase(backup.get(rSrchIndex).get(2)))&&
             (report.get(i).get(3).equalsIgnoreCase(backup.get(rSrchIndex).get(3)))&&
             (report.get(i).get(4).equalsIgnoreCase(backup.get(rSrchIndex).get(4)))&&
             (report.get(i).get(5).equalsIgnoreCase(backup.get(rSrchIndex).get(5)))&&
             (report.get(i).get(6).equalsIgnoreCase(backup.get(rSrchIndex).get(6)))&&
             (report.get(i).get(7).equalsIgnoreCase(backup.get(rSrchIndex).get(7)))&&
             (report.get(i).get(8).equalsIgnoreCase(backup.get(rSrchIndex).get(8)))&&
             (report.get(i).get(9).equalsIgnoreCase(backup.get(rSrchIndex).get(9)))
            ) 
          b=i;
        }
     
    backup.remove(rSrchIndex);      report.remove(b);
    xmlArrayWrite(backup,"BackUp"); xmlArrayWrite(report,"Report");
    outputtext.setText("");
    rSrchCount=-1;
    rSrchIndex=-1;
    outputtext.setText("\n\n\n\n\n                                            "
            + "                                                 "
            + "                             "
            + "               Deleted !");   
    }//GEN-LAST:event_edit_deleteActionPerformed

    private void rSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rSearchFocusLost
       rSrchCount=-1;
     
    }//GEN-LAST:event_rSearchFocusLost

    private void resume1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resume1ActionPerformed
        OTR=new ArrayList<>();
        String svlan="";  String textotr="";      
      try{   
            
        // select second-vlan
        String x=outputtext.getSelectedText();
        StringSelection dat =new StringSelection (x);
        clip.setContents(dat,dat); 
        if(clip.getContents(clip).isDataFlavorSupported(DataFlavor.stringFlavor)) 
        {svlan=((String)(clip.getContents(clip).getTransferData(DataFlavor.stringFlavor))).trim();}
        ////////////////////
    }catch(Exception e){}
        textotr=outputtext.getText().trim();
        outputtext.setText(""); 
        findRemark.setText("");
        outputtext.append("  \n\n"+"       "+textotr); 
        ArrayList <String> resumed=resume(textotr,svlan);
        outputtext.append("\n\n");
        for(String R:resumed)outputtext.append("\n"+"   "+R);
      
    }//GEN-LAST:event_resume1ActionPerformed

    private void export_Msag_DataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_export_Msag_DataActionPerformed
        // TODO add your handling code here:
        outputtext.setText("");
        outputtext.setText("\n\n\n\n\n                                          Processing ..........");
        
        
        String TYPE = coper.getSelectedObjects()[0]+" "+fiber.getSelectedObjects()[0];
        //copySrcDest("reset/dataReport.xml","dataReport.xml");       
       ArrayList<String> DATA=iphostDataA;
       ArrayList<ArrayList<String>> report=xmlArrayRead("dataReport");      
       ArrayList<Integer> findAllIndex= new ArrayList<>();
       ArrayList<String> vpnData= new ArrayList<>();
       ArrayList<String> d= new ArrayList<>();

        String bbInt="#$",mVlan,msVlan,pInt="",pVlan,pExVlan,vpnInt="",vpnVlan="",vpnExVlan,
                     nameData,wanData,speedData,msagData,name,VVlan,blockVlan,blockName,productData,type="";
        String vlanData="0";
        vpnInt=A[searchIndex][6];
        if(A[searchIndex][4].contains("/")){
            String mm=A[searchIndex][4].trim();
            int ind=mm.indexOf("/");
            bbInt="slot "+mm.substring(0,ind)+" port "+mm.substring(ind+1,mm.length());
            pInt="gei_"+A[searchIndex][4];
        }

        if(!((A[searchIndex][4].contains("/")))){
            bbInt="smartgroup"+A[searchIndex][4];
            pInt="smartgroup"+A[searchIndex][4]+".";
        }

        //@@@@@@@@@@@@@@@@@@@
        ArrayList<String> allVlan=new ArrayList<>();
        for (int j = 1001; j < 1601; j++) {
            allVlan.add(""+j);
        }
        //@@@@@@@@@@@@@@@@@@

 for(int i=0;i<DATA.size();i++){
            ArrayList<String> data= new ArrayList<>();
             name=DATA.get(i);

            /////////////////////////////////   blocked ip host vlan
           
 if((name.contains("ip host")||name.contains("ip-host"))&&(name.contains("vlan"))&&(name.contains("up-rate")||(name.contains("author-temp-name")))&&(!(name.contains("second-vlan")||name.contains("sec-vlan")))&&
                (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){
                d=bbParamFind(name);
                 blockName=d.get(d.size()-1).trim();
                 blockVlan=blockName.substring(blockName.length()-4,blockName.length());
                msagData=A[searchIndex][0];
                productData="Broadband Internet";
                VVlan=numSplit(blockVlan).get(numSplit(blockVlan).size()-1); 
              
                vlanData=VVlan;                                                                        // VLAN
                nameData = blockName.substring(0,blockName.length()-4);                           //Name
                wanData=d.get(0);                                                              //Wam IP
                if(name.contains("smartgroup")){
                    mVlan=d.get(2);
                   
                   
                    if(str2num((d.get(4)+""))>512) speedData=(str2num((d.get(4)+""))/1024)+" M";else speedData=(d.get(4)+"")+" K";  
                                                    //Speed
                    if(((str2num((d.get(4)+""))/1024)>8)||
                            (str2num(vlanData)>=1420)&&
                            (str2num(vlanData)<1500)||((str2num(vlanData)<1211)&&
                            (str2num(vlanData)>1200)))
                         type="FIBER";
                    
                    else type="COPER";
                }else {
                    mVlan=d.get(4);
                    if(str2num(d.get(6))>512) speedData=(str2num(d.get(6))/1024)+" M";else speedData=d.get(6)+" K";                 // Speed
                    if(((str2num(d.get(6))/1024)>8)||((str2num(vlanData)>=1420)&&
                            str2num(vlanData)<1500)||((str2num(vlanData)<1211)&&
                            (str2num(vlanData)>1200)))type="FIBER"; else type="COPER";
                }
                if(mVlan.equals(A[searchIndex][2])) {
            data.add(accessNameFinder(nameData).get(1));data.add(accessNameFinder(nameData).get(0));data.add(wanData);
            data.add(vlanData);data.add(speedData);
            data.add(productData);data.add(msagData);data.add(type);
                   
                if(TYPE.contains(type)) report.add(data);
                
                }
              
            }
            
                                                                                         //            Active customer
     // wan inter slot port Vlan second-Vlan speed Name
     if((name.contains("ip host")||name.contains("ip-host"))&&(name.contains("vlan"))&&
             (name.contains("second-vlan")||name.contains("sec-vlan"))&&
             (name.contains("up-rate")||name.contains("author-temp-name"))&&
                    (name.contains(bbInt))&&(checkIpRange(bbParamFind(name).get(0)))){
                    msagData=A[searchIndex][0];
                    productData="Broadband Internet";
                    d=bbParamFind(name);
                    wanData=d.get(0);
                    if(name.contains("smartgroup")){
                        mVlan=d.get(2);
                        msVlan=d.get(3);
                        vlanData=d.get(3);
                        if(str2num(d.get(4))>512) speedData=(str2num(d.get(4))/1024)+" M";else speedData=d.get(4)+" K";
                        nameData=d.get(5);
                        if(((str2num(d.get(4))/1024)>8)||((str2num(vlanData)>=1420)&&
                            str2num(vlanData)<1500)||((str2num(vlanData)<1211)&&
                            (str2num(vlanData)>1200)))type="FIBER"; else type="COPER";
                    }else {
                        mVlan=d.get(4);
                        msVlan=d.get(5);
                        vlanData=d.get(5);
                       if(str2num(d.get(6))>512) speedData=(str2num(d.get(6))/1024)+" M";else speedData=d.get(6)+" K";
                        nameData=d.get(7);
                        if(((str2num(d.get(6))/1024)>8)||((str2num(vlanData)>=1420)&&
                            str2num(vlanData)<1500)||((str2num(vlanData)<1211)&&
                            (str2num(vlanData)>1200)))type="FIBER"; else type="COPER";
                    }
                    if(mVlan.equals(A[searchIndex][2])) {
//                        if(!msVlan.equals(null))
//                            data.add(msVlan);
                         data.add(accessNameFinder(nameData).get(1));data.add(accessNameFinder(nameData).get(0));data.add(wanData);data.add(vlanData);data.add(speedData);
                         data.add(productData);data.add(msagData);data.add(type);
                      if(TYPE.contains(type)) report.add(data); 
                    }
                     
                }

                //////////////////////////////////  public IP
//////
               
if((name.contains("internal-vlan "))&&(name.contains("external-vlan "))){
                    if(DATA.get(i-1).contains(pInt)) {
                        if (DATA.get(i+1).contains("ip address")){
                            pExVlan=strSplit(name).get(strSplit(name.trim()).size()-1);
                            if(pExVlan.equals(A[searchIndex][2])){
                                if(checkIpRange(midString(DATA.get(i+1),"ip address "," "))){
                                    d=ppParamFind(arrayList2Str(findAllData(DATA.get(i-1).trim())));
                                    nameData=d.get(4);
                                    wanData=d.get(3);
                                    vlanData=d.get(1);                                   
                                    if(d.get(5).equals("66") ||d.get(5).equals("256")||d.get(5).equals("512")) speedData=d.get(5)+" k";
                                    else speedData=d.get(5)+" M";
                                    productData="Broadband Internet (public)";
                                    msagData=A[searchIndex][0];
                                    if(((str2num(d.get(5))/1024)>8)||((str2num(vlanData)>=1420)&&
                                           str2num(vlanData)<1500)||((str2num(vlanData)<1211)&&
                                             (str2num(vlanData)>1200)))type="FIBER"; else type="COPER";
                                    
                                   data.add(accessNameFinder(nameData).get(1));data.add(accessNameFinder(nameData).get(0));data.add(wanData);data.add(vlanData);data.add(speedData);
                                    data.add(productData);data.add(msagData);data.add(type);data.add(d.get(0));data.add("DATA");
                                   if(TYPE.contains(type)) report.add(data);
                                    
                                    
                                }
                            }
                        }
                    }
                }
//////
                //////////////////////////////////  VPN
                if((name.contains("internal-vlanid"))&&(name.contains("external-vlanid"))){
                    if(DATA.get(i-1).contains(vpnInt)) {
                        vpnExVlan=strSplit(name).get(strSplit(name.trim()).size()-1);
                        if(vpnExVlan.equals(A[searchIndex][2])){
//                             String ErHostName=("hostname "+String.valueOf(A[searchIndex][17])+"-ER-DATA-");
                            vpnData=findAllVpnData(DATA.get(i-1).trim());
                            findAllIndex= searchIndex(vpnData,"ip address");
                            if(!(findAllIndex.isEmpty())) {
                                if(checkIpRange(midString(vpnData.get(findAllIndex.get(0)),"ip address "," "))){
                                    d=vpnParamFind(arrayList2Str(findAllVpnData(DATA.get(i-1).trim())));
                                    nameData=d.get(1);
                                    wanData=d.get(3);
                                  
                                    vlanData=d.get(4);
                                    if(d.get(6).equals("66") ||d.get(6).equals("256")||d.get(6).equals("512")) speedData=d.get(6)+" K";
                                    else speedData=d.get(6)+" M";
                                    if(d.get(2).equals("DATA"))productData="Broadband Internet (public)";else productData="Broadband VPN";
                                    msagData=A[searchIndex][0];
                                    if(((str2num(d.get(6))/1024)>8)||((str2num(vlanData)>=1420)&&
                                          str2num(vlanData)<1500)||((str2num(vlanData)<1211)&&
                                             (str2num(vlanData)>1200)))type="FIBER"; else type="COPER";
                                    data.add(accessNameFinder(nameData).get(1));data.add(accessNameFinder(nameData).get(0));data.add(wanData);data.add(vlanData);data.add(speedData);
                                    data.add(productData);data.add(msagData);data.add(type);data.add(d.get(0));data.add(d.get(2));
                                  
                                    if(TYPE.contains(type))  report.add(data);
                               
                                }
                            }
                        }
                    }
                }
          }
 
  xmlArrayWrite(removeDoublicate(report),"dataReport");
//  xmlArrayWrite(report,"dataReportTest");
  
        outputtext.setText("\n\n\n\n\n      Complted !!");      
        
    }//GEN-LAST:event_export_Msag_DataActionPerformed

    private void DresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DresetActionPerformed
        // TODO add your handling code here:
       copySrcDest("reset/dataReport.xml","dataReport.xml");
    }//GEN-LAST:event_DresetActionPerformed

    private void DsaveExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DsaveExcelActionPerformed
        // TODO add your handling code here:
        new JFXPanel();
        Platform.runLater(()->{
            outputtext.setText("");
            FileChooser fc=new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File","*.xls"));
            fc.setTitle("Save As");
            File file=fc.showSaveDialog(null);
            String path =(""+file+"_"+cDate).replace(".xls","");
            xlWrite("dataReport",path);
        });
    }//GEN-LAST:event_DsaveExcelActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(searchJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(searchJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(searchJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(searchJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new searchJFrame().setVisible(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(searchJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Data;
    private javax.swing.JMenuItem Dreset;
    private javax.swing.JMenuItem DsaveExcel;
    private javax.swing.JLabel area;
    private javax.swing.JMenuItem backupBRER;
    private javax.swing.JTextField bbAccess;
    private javax.swing.JTextField bbName;
    private javax.swing.JButton bbReset;
    private javax.swing.JButton bbRun;
    private javax.swing.JTextField bbSpeed;
    private javax.swing.JTextField bbVlan;
    private javax.swing.JTextField bbWanIp;
    private javax.swing.JButton block;
    private javax.swing.JTextField changevlan;
    private javax.swing.JButton clear;
    private javax.swing.JCheckBoxMenuItem coper;
    private javax.swing.JMenuItem copy;
    private javax.swing.JMenuItem cut;
    private javax.swing.JMenuItem dailyReport;
    private javax.swing.JMenuItem database;
    private javax.swing.JMenu edit;
    private javax.swing.JMenuItem edit_delete;
    private javax.swing.JTextField eponMac;
    private javax.swing.JTextField eponName;
    private javax.swing.JTextField eponPort;
    private javax.swing.JButton eponRest;
    private javax.swing.JButton eponRun;
    private javax.swing.JTextField eponSpeed;
    private javax.swing.JTextField eponVlan;
    private javax.swing.JMenuItem export_Msag_Data;
    private javax.swing.JCheckBoxMenuItem fiber;
    private javax.swing.JLabel findRemark;
    private javax.swing.JButton findall;
    private javax.swing.JButton findp;
    private javax.swing.JMenuItem help;
    private javax.swing.JButton iImport;
    private javax.swing.JPanel internet;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLMsag;
    private javax.swing.JLabel jLMsagIp;
    private javax.swing.JLabel jLVlan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel jpepon;
    private javax.swing.JPanel jpinternet;
    private javax.swing.JPanel jpolt;
    private javax.swing.JPanel jppublicEr;
    private javax.swing.JPanel jpvpn;
    private javax.swing.JTextField mduPort;
    private javax.swing.JButton mduReset;
    private javax.swing.JButton mduRun;
    private javax.swing.JTextField mduSpeed;
    private javax.swing.JTextField mduVlan;
    private javax.swing.JMenuBar menu;
    private javax.swing.JButton mnRest;
    private javax.swing.JTextField msag;
    private javax.swing.JTextField msagIp;
    private javax.swing.JLabel msagTag;
    private javax.swing.JTextField msanPort;
    private javax.swing.JButton msanResetPort;
    private javax.swing.JTextField msanVlan;
    private javax.swing.JTextField oltGemPort;
    private javax.swing.JButton oltReset;
    private javax.swing.JButton oltRun;
    private javax.swing.JTextField oltSpeed;
    private javax.swing.JTextField oltVlan;
    private javax.swing.JTextArea outputtext;
    private javax.swing.JMenuItem overAllReport;
    private javax.swing.JTextField pAccess;
    private javax.swing.JButton pImport;
    private javax.swing.JTextField pLanIp;
    private javax.swing.JTextField pName;
    private javax.swing.JButton pReset;
    private javax.swing.JButton pRun;
    private javax.swing.JTextField pSpeed;
    private javax.swing.JTextField pSubnet;
    private javax.swing.JTextField pVlan;
    private javax.swing.JTextField pWanIp;
    private javax.swing.JMenuItem paste;
    private javax.swing.JPanel pp;
    private javax.swing.JTextField rSearch;
    private javax.swing.JButton rSearchOK;
    private javax.swing.JLabel rSearchRemark;
    private javax.swing.JLabel rSrchRemark;
    private javax.swing.JButton replacespeed;
    private javax.swing.JButton replacevlan;
    private javax.swing.JButton reset;
    private javax.swing.JMenuItem resetOverAllReport;
    private javax.swing.JMenuItem resetReport;
    private javax.swing.JButton resume1;
    private javax.swing.JButton save;
    private javax.swing.JButton search;
    private javax.swing.JComboBox<String> searchItem;
    private javax.swing.JTabbedPane selector;
    private javax.swing.JTextArea stickyNote;
    private javax.swing.JButton terminate;
    private javax.swing.JTextField vlan;
    private javax.swing.JLabel vlanTag;
    private javax.swing.JPanel vpn;
    private javax.swing.JTextField vpnAccess;
    private javax.swing.JButton vpnImnort;
    private javax.swing.JTextField vpnLanIp;
    private javax.swing.JTextField vpnName;
    private javax.swing.JButton vpnReset;
    private javax.swing.JButton vpnRun;
    private javax.swing.JTextField vpnSpeed;
    private javax.swing.JTextField vpnSubnet;
    private javax.swing.JTextField vpnVlan;
    private javax.swing.JTextField vpnVrf;
    private javax.swing.JTextField vpnVrrp;
    private javax.swing.JTextField vpnWanIp;
    // End of variables declaration//GEN-END:variables
}
