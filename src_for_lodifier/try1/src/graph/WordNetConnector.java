/*     */ package graph;
/*     */ 
/*     */ import io.DocumentReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class WordNetConnector
/*     */ {
/*     */   public String getSynset(String[] wordsenses)
/*     */     throws IOException
/*     */   {
/*  12 */     ArrayList triples = new ArrayList();
/*  13 */     DocumentReader reader = new DocumentReader();
/*  14 */     String[] lines = reader.readFile("full/wordnet-wordsense-synset-relations.ttl");
/*     */     String[] arrayOfString1;
/*  15 */     String str2 = (arrayOfString1 = wordsenses).length; for (String str1 = 0; str1 < str2; str1++) { String wordsense = arrayOfString1[str1];
/*  16 */       for (line : lines) {
/*  17 */         if (line.contains(wordsense + " ")) {
/*  18 */           triples.add(line);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  23 */     String[] lines2 = reader.readFile("full/wordnet-derivationallyrelated.ttl");
/*  24 */     String str3 = (line = wordsenses).length;
/*     */     Object line2;
/*  24 */     for (str2 = 0; str2 < str3; str2++) { String wordsense = line[str2];
/*  25 */       for (line2 : lines2) {
/*  26 */         if (((String)line2).contains(wordsense + " ")) {
/*  27 */           triples.add(line2);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  32 */     String[] lines3 = reader.readFile("full/wordnet-participleof.ttl");
/*  33 */     String line = (line2 = wordsenses).length;
/*     */     Object line3;
/*  33 */     for (str3 = 0; str3 < line; str3++) { String wordsense = line2[str3];
/*  34 */       for (line3 : lines3) {
/*  35 */         if (((String)line3).contains(wordsense + " ")) {
/*  36 */           triples.add(line3);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  41 */     String[] lines4 = reader.readFile("full/wordnet-pertainsto.ttl");
/*  42 */     String str4 = (line3 = wordsenses).length;
/*     */     Object line4;
/*  42 */     for (line = 0; line < str4; line++) { String wordsense = line3[line];
/*  43 */       for (line4 : lines4) {
/*  44 */         if (((String)line4).contains(wordsense + " ")) {
/*  45 */           triples.add(line4);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  50 */     String[] lines7 = reader.readFile("full/wordnet-antonym.ttl");
/*  51 */     String str6 = (line4 = wordsenses).length;
/*     */     Object line7;
/*  51 */     for (String str5 = 0; str5 < str6; str5++) { String wordsense = line4[str5];
/*  52 */       for (line7 : lines7) {
/*  53 */         if (((String)line7).contains(wordsense + " ")) {
/*  54 */           triples.add(line7);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  59 */     String[] lines5 = reader.readFile("wordnet-attribute.ttl");
/*  60 */     String str8 = (line7 = wordsenses).length;
/*     */     Object line5;
/*  60 */     for (String str7 = 0; str7 < str8; str7++) { String wordsense = line7[str7];
/*  61 */       for (line5 : lines5) {
/*  62 */         if (((String)line5).contains(wordsense + " ")) {
/*  63 */           triples.add(line5);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  68 */     String[] lines6 = reader.readFile("wordnet-hyponym.ttl");
/*  69 */     String str10 = (line5 = wordsenses).length;
/*     */     Object line6;
/*  69 */     for (String str9 = 0; str9 < str10; str9++) { String wordsense = line5[str9];
/*  70 */       for (line6 : lines6) {
/*  71 */         if (((String)line6).contains(wordsense + " ")) {
/*  72 */           triples.add(line6);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  78 */     String[] lines10 = reader.readFile("wordnet-similarity.ttl");
/*  79 */     String str12 = (line6 = wordsenses).length;
/*     */     Object line10;
/*  79 */     for (String str11 = 0; str11 < str12; str11++) { String wordsense = line6[str11];
/*  80 */       for (line10 : lines10) {
/*  81 */         if (((String)line10).contains(wordsense + " ")) {
/*  82 */           triples.add(line10);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  87 */     String[] lines8 = reader.readFile("wordnet-entailment.ttl");
/*  88 */     Object localObject1 = (line10 = wordsenses).length; for (String str13 = 0; str13 < localObject1; str13++) { String wordsense = line10[str13];
/*  89 */       for (String line8 : lines8) {
/*  90 */         if (line8.contains(wordsense + " ")) {
/*  91 */           triples.add(line8);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  96 */     StringBuilder sb = new StringBuilder();
/*  97 */     for (localObject1 = triples.iterator(); ((Iterator)localObject1).hasNext(); ) { String line = (String)((Iterator)localObject1).next();
/*  98 */       sb.append(line);
/*  99 */       sb.append("\n");
/*     */     }
/* 101 */     String wnTriples = sb.toString();
/* 102 */     System.out.println(wnTriples);
/* 103 */     return wnTriples;
/*     */   }
/*     */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     graph.WordNetConnector
 * JD-Core Version:    0.6.2
 */