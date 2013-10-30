/*    */ package data;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Story
/*    */ {
/*    */   private static int story_nr;
/* 13 */   private HashMap entity = new HashMap();
/*    */   private String description;
/*    */ 
/*    */   public Story(String p_description, HashMap p_entity)
/*    */   {
/* 17 */     this.description = p_description;
/* 18 */     this.entity = p_entity;
/*    */   }
/*    */ 
/*    */   public Story() {
/* 22 */     this.description = "";
/* 23 */     this.entity = new HashMap();
/*    */   }
/*    */ 
/*    */   public static int getStoryNr()
/*    */   {
/* 31 */     return story_nr;
/*    */   }
/*    */ 
/*    */   public static void setStoryNr(int nr)
/*    */   {
/* 39 */     story_nr = nr;
/*    */   }
/*    */ 
/*    */   public void setDescription(String p_description)
/*    */   {
/* 47 */     this.description = p_description;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 55 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setEntity(String p_key, String p_value)
/*    */   {
/* 64 */     this.entity.put(p_key, p_value);
/*    */   }
/*    */ 
/*    */   public void setEntity(HashMap entity)
/*    */   {
/* 72 */     this.entity = entity;
/*    */   }
/*    */ 
/*    */   public HashMap getEntitiy()
/*    */   {
/* 80 */     return this.entity;
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     data.Story
 * JD-Core Version:    0.6.2
 */