/*    */ package data;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class Triples
/*    */ {
/* 12 */   private static String rdf = "";
/* 13 */   private static ArrayList<String> variable_list = new ArrayList();
/*    */ 
/*    */   public static ArrayList<String> getVariableList()
/*    */   {
/* 20 */     return variable_list;
/*    */   }
/*    */ 
/*    */   public static String getNextAvailableVariable(String prefix)
/*    */   {
/* 29 */     int i = 0;
/* 30 */     boolean b = false;
/* 31 */     String hv = "";
/* 32 */     while (!b) {
/* 33 */       hv = prefix + i;
/* 34 */       if (variable_list.contains(hv)) {
/* 35 */         i++;
/*    */       }
/*    */       else {
/* 38 */         b = true;
/*    */       }
/*    */     }
/*    */ 
/* 42 */     return hv;
/*    */   }
/*    */ 
/*    */   public static void setVariable_List(String variable)
/*    */   {
/* 50 */     variable_list.add(variable);
/*    */   }
/*    */ 
/*    */   public static String getRdf()
/*    */   {
/* 58 */     return rdf;
/*    */   }
/*    */ 
/*    */   public static void setRdfList(String triple)
/*    */   {
/* 66 */     rdf = rdf.concat(triple);
/*    */   }
/*    */ 
/*    */   public static void setRdf(String value)
/*    */   {
/* 74 */     rdf = value;
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     data.Triples
 * JD-Core Version:    0.6.2
 */