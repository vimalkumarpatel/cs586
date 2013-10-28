/*     */ package pipeline;
/*     */ 
/*     */ import data.Story;
/*     */ import data.Triples;
/*     */ import graph.DBPediaConnector;
/*     */ import graph.WordNetConnector;
/*     */ import io.DocumentReader;
/*     */ import io.DocumentWriter;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import ner.SimpleNER;
/*     */ import ner.Wikifier;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ import preprocessing.Boxer;
/*     */ import preprocessing.CC;
/*     */ import preprocessing.Tokenizer;
/*     */ import rdf.RelationshipExtraction;
/*     */ import ui.UserInterface;
/*     */ import wn.Lemmatizer;
/*     */ import wn.UKB;
/*     */ import wn.WSD;
/*     */ 
/*     */ public class Pipeline
/*     */ {
/*     */   private static String rdf;
/*     */ 
/*     */   public void startPipeline()
/*     */     throws IOException
/*     */   {
/*  55 */     RelationshipExtraction re = new RelationshipExtraction();
/*  56 */     UserInterface ui = new UserInterface();
/*  57 */     DocumentWriter dw = new DocumentWriter();
/*     */ 
/*  59 */     String rdf = "";
/*  60 */     String[] strar = startTokenizer(UserInterface.getCC_input());
/*     */ 
/*  62 */     Story[] stories = new Story[strar.length];
/*     */ 
/*  64 */     String wikified = startNER(strar, stories);
/*  65 */     String cc_output = "";
/*  66 */     cc_output = startParser(wikified);
/*     */ 
/*  68 */     String lemmatized_input = "";
/*  69 */     lemmatized_input = startLemmatizer(cc_output);
/*     */ 
/*  72 */     if (UserInterface.getLemmatizer_output().length() != 0) {
/*  73 */       BufferedWriter out = new BufferedWriter(new FileWriter(UserInterface.getLemmatizer_output()));
/*  74 */       out.write(lemmatized_input);
/*  75 */       out.close();
/*     */     }
/*     */ 
/*  78 */     String ukb_output = "";
/*  79 */     ukb_output = startUKB();
/*     */ 
/*  81 */     String[] uris = startWSD(ukb_output, UserInterface.getUKB_path());
/*     */ 
/*  83 */     String boxer_output = "";
/*  84 */     boxer_output = startBoxer();
/*     */ 
/*  86 */     rdf = startRDF(re, boxer_output, stories);
/*  87 */     WSD wsd = new WSD();
/*  88 */     rdf = wsd.addWordNetURIs(rdf, uris);
/*     */ 
/*  93 */     if (UserInterface.getRdf_output_file().length() != 0) {
/*  94 */       BufferedWriter out = new BufferedWriter(new FileWriter(UserInterface.getRdf_output_file()));
/*  95 */       out.write(rdf);
/*     */ 
/*  97 */       out.close();
/*     */     }
/*  99 */     re.deleteUris();
/*     */   }
/*     */ 
/*     */   public String startPipeline2(int bnodes, float prob, int wn_links)
/*     */     throws IOException
/*     */   {
/* 109 */     RelationshipExtraction re = new RelationshipExtraction();
/* 110 */     UserInterface ui = new UserInterface();
/* 111 */     DocumentWriter dr = new DocumentWriter();
/*     */ 
/* 113 */     String rdf = "";
/* 114 */     String[] uris = (String[])null;
/* 115 */     String[] strar = startTokenizer(UserInterface.getCC_input());
/*     */ 
/* 117 */     Story[] stories = new Story[strar.length];
/* 118 */     String wikified = startNER2(strar, stories, prob);
/*     */ 
/* 120 */     String cc_output = "";
/* 121 */     cc_output = startParser(wikified);
/*     */ 
/* 123 */     if (wn_links == 1) {
/* 124 */       String lemmatized_input = "";
/* 125 */       lemmatized_input = startLemmatizer(cc_output);
/* 126 */       dr.writeFile(UserInterface.getLemmatizer_output(), lemmatized_input);
/*     */ 
/* 128 */       String ukb_output = "";
/* 129 */       ukb_output = startUKB();
/*     */ 
/* 131 */       uris = startWSD(ukb_output, UserInterface.getUKB_path());
/*     */     }
/*     */ 
/* 134 */     String boxer_output = "";
/* 135 */     boxer_output = startBoxer();
/*     */ 
/* 137 */     if (bnodes == 0) {
/* 138 */       rdf = startRDF(re, boxer_output, stories);
/*     */     }
/*     */     else {
/* 141 */       rdf = startRDF2(re, boxer_output, stories);
/*     */     }
/*     */ 
/* 144 */     if (wn_links == 1) {
/* 145 */       WSD wsd = new WSD();
/* 146 */       rdf = wsd.addWordNetURIs2(rdf, uris);
/*     */     }
/* 148 */     re.deleteUris();
/* 149 */     return rdf;
/*     */   }
/*     */ 
/*     */   public String[] startTokenizer(String s)
/*     */   {
/* 159 */     Tokenizer tok = new Tokenizer();
/* 160 */     return tok.tokenize(s);
/*     */   }
/*     */ 
/*     */   public String startNER(String[] toks, Story[] stories)
/*     */     throws IOException
/*     */   {
/* 173 */     Wikifier wiki = new Wikifier();
/* 174 */     StringEscapeUtils seu = new StringEscapeUtils();
/* 175 */     SimpleNER ner = new SimpleNER();
/*     */ 
/* 177 */     String cc_input = "";
/* 178 */     for (int i = 0; i <= stories.length - 1; i++) {
/* 179 */       stories[i] = new Story();
/* 180 */       stories[i].setDescription(toks[i]);
/* 181 */       cc_input = cc_input.concat("\n");
/* 182 */       if (stories[i] != null) {
/* 183 */         String text_wikified = Wikifier.wikify(stories[i].getDescription());
/* 184 */         System.out.println(text_wikified);
/* 185 */         HashMap entity = new HashMap();
/* 186 */         entity = Wikifier.getWikipediaLinks(text_wikified);
/* 187 */         System.out.println("Entity: " + entity);
/* 188 */         stories[i].setEntity(entity);
/* 189 */         String description = stories[i].getDescription();
/* 190 */         Iterator it = entity.keySet().iterator();
/* 191 */         while (it.hasNext()) {
/* 192 */           String key2 = (String)it.next();
/* 193 */           String key = key2.replace("_", " ");
/* 194 */           key = StringEscapeUtils.escapeJava(key);
/* 195 */           description = description.replace(key, key2);
/*     */         }
/* 197 */         description = ner.markNE(description);
/* 198 */         cc_input = cc_input.concat(description + " ");
/* 199 */         String meta = "<META>'Text 1'";
/* 200 */         cc_input = meta.concat(cc_input);
/* 201 */         System.out.println(cc_input);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 206 */     return cc_input;
/*     */   }
/*     */ 
/*     */   public String startNER(String[] toks, Story[] stories, float prob)
/*     */     throws IOException
/*     */   {
/* 219 */     Wikifier wiki = new Wikifier();
/* 220 */     StringEscapeUtils seu = new StringEscapeUtils();
/* 221 */     SimpleNER ner = new SimpleNER();
/*     */ 
/* 223 */     String cc_input = "";
/* 224 */     for (int i = 0; i <= stories.length - 1; i++) {
/* 225 */       stories[i] = new Story();
/* 226 */       stories[i].setDescription(toks[i]);
/* 227 */       cc_input = cc_input.concat("\n");
/* 228 */       if (stories[i] != null) {
/* 229 */         String text_wikified = Wikifier.wikify(stories[i].getDescription(), prob);
/* 230 */         System.out.println(text_wikified);
/* 231 */         HashMap entity = new HashMap();
/* 232 */         entity = Wikifier.getWikipediaLinks(text_wikified);
/* 233 */         stories[i].setEntity(entity);
/* 234 */         String description = stories[i].getDescription();
/* 235 */         Iterator it = entity.keySet().iterator();
/* 236 */         while (it.hasNext()) {
/* 237 */           String key2 = (String)it.next();
/* 238 */           String key = key2.replace("_", " ");
/* 239 */           key = StringEscapeUtils.escapeJava(key);
/* 240 */           description = description.replace(key, key2);
/*     */         }
/* 242 */         description = ner.markNE(description);
/* 243 */         cc_input = cc_input.concat(description + " ");
/* 244 */         String meta = "<META>'Text 1'";
/* 245 */         cc_input = meta.concat(cc_input);
/* 246 */         System.out.println(cc_input);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 251 */     return cc_input;
/*     */   }
/*     */ 
/*     */   public String startNER2(String[] toks, Story[] stories, float prob)
/*     */     throws IOException
/*     */   {
/* 264 */     Wikifier wiki = new Wikifier();
/* 265 */     StringEscapeUtils seu = new StringEscapeUtils();
/* 266 */     SimpleNER ner = new SimpleNER();
/*     */ 
/* 268 */     String cc_input = "";
/* 269 */     for (int i = 0; i <= stories.length - 1; i++) {
/* 270 */       stories[i] = new Story();
/* 271 */       stories[i].setDescription(toks[i]);
/* 272 */       cc_input = cc_input.concat("\n");
/* 273 */       if (stories[i] != null) {
/* 274 */         String text_wikified = Wikifier.wikify(stories[i].getDescription(), prob);
/* 275 */         System.out.println(text_wikified);
/* 276 */         HashMap entity = new HashMap();
/* 277 */         entity = Wikifier.getWikipediaLinks2(text_wikified);
/* 278 */         stories[i].setEntity(entity);
/* 279 */         String description = stories[i].getDescription();
/* 280 */         Iterator it = entity.keySet().iterator();
/* 281 */         while (it.hasNext()) {
/* 282 */           String key2 = (String)it.next();
/* 283 */           String key = key2.replace("_", " ");
/* 284 */           key = StringEscapeUtils.escapeJava(key);
/* 285 */           description = description.replace(key, key2);
/*     */         }
/* 287 */         description = ner.markNE(description);
/* 288 */         cc_input = cc_input.concat(description + " ");
/* 289 */         String meta = "<META>'Text 1'";
/* 290 */         cc_input = meta.concat(cc_input);
/* 291 */         System.out.println(cc_input);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 298 */     return cc_input;
/*     */   }
/*     */ 
/*     */   public String startParser(String text)
/*     */     throws IOException
/*     */   {
/* 306 */     UserInterface ui = new UserInterface();
/* 307 */     CC cc = new CC();
/* 308 */     String cc_output_file = UserInterface.getCC_output();
/* 309 */     String cc_output = "";
/*     */     try {
/* 311 */       CC.doExecCC(text, UserInterface.getBoxer_path(), UserInterface.getCC_output());
/*     */     }
/*     */     catch (IOException e1) {
/* 314 */       e1.printStackTrace();
/*     */     }
/*     */     try {
/* 317 */       StringEscapeUtils seu1 = new StringEscapeUtils();
/* 318 */       FileReader fileReader = new FileReader(cc_output_file);
/* 319 */       BufferedReader bufferedReader = new BufferedReader(fileReader);
/* 320 */       String line = null;
/* 321 */       while ((line = bufferedReader.readLine()) != null) {
/* 322 */         cc_output = cc_output + StringEscapeUtils.escapeJava(line) + "\n";
/*     */       }
/* 324 */       bufferedReader.close();
/*     */     }
/*     */     catch (IOException e) {
/* 327 */       e.printStackTrace();
/*     */     }
/* 329 */     return cc_output;
/*     */   }
/*     */ 
/*     */   public String startLemmatizer(String cc_output)
/*     */   {
/* 338 */     Lemmatizer lem = new Lemmatizer();
/* 339 */     String lemmatized_input = "";
/* 340 */     lemmatized_input = lem.lemmatize(cc_output);
/* 341 */     System.out.println(lemmatized_input);
/* 342 */     return lemmatized_input;
/*     */   }
/*     */ 
/*     */   public String startUKB() {
/* 346 */     UserInterface ui = new UserInterface();
/* 347 */     String lemmatizer_output_file = UserInterface.getLemmatizer_output();
/* 348 */     String ukb_output_file = UserInterface.getUKB_output();
/* 349 */     UKB ukb = new UKB();
/* 350 */     String ukb_output = "";
/*     */     try {
/* 352 */       ukb.doExecUKB(UserInterface.getUKB_path(), ukb_output_file, lemmatizer_output_file);
/*     */     }
/*     */     catch (IOException e1) {
/* 355 */       e1.printStackTrace();
/*     */     }
/*     */     try {
/* 358 */       StringEscapeUtils seu1 = new StringEscapeUtils();
/* 359 */       FileReader fileReader = new FileReader(ukb_output_file);
/* 360 */       BufferedReader bufferedReader = new BufferedReader(fileReader);
/* 361 */       String line = null;
/* 362 */       while ((line = bufferedReader.readLine()) != null) {
/* 363 */         ukb_output = ukb_output + StringEscapeUtils.escapeJava(line) + "\n";
/*     */       }
/* 365 */       bufferedReader.close();
/*     */     }
/*     */     catch (IOException e) {
/* 368 */       e.printStackTrace();
/*     */     }
/* 370 */     System.out.println(ukb_output);
/* 371 */     return ukb_output;
/*     */   }
/*     */ 
/*     */   public String[] startWSD(String ukb_output, String ukb_path)
/*     */     throws IOException
/*     */   {
/* 382 */     WSD wsd = new WSD();
/* 383 */     DocumentReader reader = new DocumentReader();
/* 384 */     ArrayList uris = new ArrayList();
/* 385 */     Matcher matcher = Pattern.compile("ctx_[0-9]+( )+w[0-9]+( )+([0-9]+-(.))( )+!!( )+(.*)").matcher(ukb_output);
/* 386 */     String word = "";
/* 387 */     String word_sense = "";
/* 388 */     String word_type = "";
/* 389 */     Integer sense_nr = Integer.valueOf(0);
/* 390 */     String[] lines = reader.readFile(ukb_path + "lkb_sources/30/wnet30_dict.txt");
/* 391 */     while (matcher.find()) {
/* 392 */       word_sense = matcher.group(3);
/* 393 */       word_type = matcher.group(4);
/* 394 */       word = matcher.group(7);
/* 395 */       word = word.replace(" ", "_");
/* 396 */       boolean isadjs = false;
/* 397 */       if (word_type.matches("a")) {
/* 398 */         sense_nr = Integer.valueOf(wsd.getSenseNr(word, word_sense, word_type, lines));
/* 399 */         String adjsat = "wn30:wordsense-".concat(word).concat("-adjectivesatellite-").concat(sense_nr.toString());
/* 400 */         String adj = "wn30:wordsense-".concat(word).concat("-adjective-").concat(sense_nr.toString());
/* 401 */         isadjs = wsd.isAdjectiveSatellite(adjsat, adj);
/* 402 */         if (!isadjs) {
/* 403 */           uris.add(adj);
/* 404 */           System.out.println(adj);
/*     */         }
/*     */         else {
/* 407 */           uris.add(adjsat);
/* 408 */           System.out.println(adjsat);
/*     */         }
/*     */       }
/* 411 */       word_type.matches("n");
/*     */ 
/* 418 */       if (word_type.matches("v")) {
/* 419 */         sense_nr = Integer.valueOf(wsd.getSenseNr(word, word_sense, word_type, lines));
/* 420 */         String verb = "wn30:wordsense-".concat(word).concat("-verb-").concat(sense_nr.toString());
/* 421 */         uris.add(verb);
/* 422 */         System.out.println(verb);
/*     */       }
/* 424 */       if (word_type.matches("r")) {
/* 425 */         sense_nr = Integer.valueOf(wsd.getSenseNr(word, word_sense, word_type, lines));
/* 426 */         String verb = "wn30:wordsense-".concat(word).concat("-adverb-").concat(sense_nr.toString());
/* 427 */         uris.add(verb);
/* 428 */         System.out.println(verb);
/*     */       }
/*     */     }
/* 431 */     return (String[])uris.toArray(new String[uris.size()]);
/*     */   }
/*     */ 
/*     */   public String startBoxer()
/*     */   {
/* 439 */     UserInterface ui = new UserInterface();
/* 440 */     String boxer_output_file = UserInterface.getBoxer_output();
/* 441 */     Boxer boxer = new Boxer();
/* 442 */     String boxer_output = "";
/*     */     try {
/* 444 */       Boxer.doExecBoxer(UserInterface.getBoxer_path(), UserInterface.getCC_output(), boxer_output_file);
/*     */     }
/*     */     catch (IOException e1) {
/* 447 */       e1.printStackTrace();
/*     */     }
/*     */     try {
/* 450 */       StringEscapeUtils seu1 = new StringEscapeUtils();
/* 451 */       FileReader fileReader = new FileReader(boxer_output_file);
/* 452 */       BufferedReader bufferedReader = new BufferedReader(fileReader);
/* 453 */       String line = null;
/* 454 */       while ((line = bufferedReader.readLine()) != null) {
/* 455 */         boxer_output = boxer_output + StringEscapeUtils.escapeJava(line);
/*     */       }
/* 457 */       bufferedReader.close();
/*     */     }
/*     */     catch (IOException e) {
/* 460 */       e.printStackTrace();
/*     */     }
/* 462 */     return boxer_output;
/*     */   }
/*     */ 
/*     */   public String startRDF(RelationshipExtraction re, String boxer_output, Story[] stories)
/*     */   {
/* 471 */     String trpls = "";
/* 472 */     Triples triples = new Triples();
/* 473 */     RelationshipExtraction.makeTriples2(boxer_output);
/* 474 */     trpls = Triples.getRdf();
/* 475 */     String rdf = "";
/* 476 */     rdf = trpls;
/* 477 */     rdf = RelationshipExtraction.writeURIs(stories, rdf);
/* 478 */     rdf = RelationshipExtraction.writePrefixes().concat(rdf);
/* 479 */     return rdf;
/*     */   }
/*     */ 
/*     */   public String startRDF2(RelationshipExtraction re, String boxer_output, Story[] stories)
/*     */   {
/* 488 */     String trpls = "";
/* 489 */     Triples triples = new Triples();
/* 490 */     RelationshipExtraction.makeTriples2(boxer_output);
/* 491 */     trpls = Triples.getRdf();
/* 492 */     String rdf = "";
/* 493 */     rdf = trpls;
/* 494 */     rdf = RelationshipExtraction.writeURIs2(stories, rdf);
/* 495 */     rdf = RelationshipExtraction.writePrefixes2().concat(rdf);
/*     */ 
/* 500 */     return rdf;
/*     */   }
/*     */ 
/*     */   public static String getRdf()
/*     */   {
/* 508 */     return rdf;
/*     */   }
/*     */ 
/*     */   public String startGraph(ArrayList<String> dbpediaLinks, String[] wnURIs, String rdf) throws IOException {
/* 512 */     DBPediaConnector db = new DBPediaConnector();
/* 513 */     WordNetConnector wn = new WordNetConnector();
/* 514 */     String dbpedia_links = DBPediaConnector.getDBPediaLinksFromFile(dbpediaLinks);
/* 515 */     String wn_links = wn.getSynset(wnURIs);
/* 516 */     String rdf_new = rdf.concat(dbpedia_links).concat(wn_links);
/* 517 */     return rdf_new;
/*     */   }
/*     */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     pipeline.Pipeline
 * JD-Core Version:    0.6.2
 */