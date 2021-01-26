package com.example.narrator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import com.example.narrator.classes.ComicChapter;
import com.example.narrator.classes.ComicScene;
import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.DialogViewClass;
import com.example.narrator.classes.ImageViewClass;
import com.example.narrator.classes.TextChapter;
import com.example.narrator.classes.TextPage;
import com.example.narrator.classes.TextStory;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDatabase.db";

    public static final String TEXT_TABLE_NAME = "TextStory";
    public static final String TEXT_CHAPTER_TABLE_NAME = "TextChapter";
    public static final String TEXT_PAGE_TABLE_NAME = "TextPage";

    public static final String COMIC_TABLE_NAME = "ComicStory";
    public static final String COMIC_CHAPTER_TABLE_NAME = "ComicChapter";
    public static final String COMIC_SCENE_TABLE_NAME = "ComicScene";
    public static final String IMAGE_TABLE_NAME = "ComicImage";
    public static final String DIALOG_TABLE_NAME = "ComicDialog";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TEXT_TABLE_NAME + " " +"(TID INTEGER PRIMARY KEY AUTOINCREMENT, UID INTEGER NOT NULL, " +
                "title TEXT NOT NULL, description TEXT NOT NULL, " +
                "coverImage TEXT NOT NULL, genre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+TEXT_CHAPTER_TABLE_NAME + " " +"(TCID INTEGER PRIMARY KEY AUTOINCREMENT, TID INTEGER NOT NULL, " +
                "title TEXT NOT NULL, " +
                "FOREIGN KEY(TID) REFERENCES COMIC_TABLE_NAME (TID))");

        db.execSQL("CREATE TABLE "+TEXT_PAGE_TABLE_NAME + " " +"(TPID INTEGER PRIMARY KEY AUTOINCREMENT, TCID INTEGER NOT NULL, " +
                "story TEXT NOT NULL, " +
                "FOREIGN KEY(TCID) REFERENCES COMIC_TABLE_NAME (TCID))");



        db.execSQL("CREATE TABLE "+COMIC_TABLE_NAME + " " +"(CID INTEGER PRIMARY KEY AUTOINCREMENT, UID INTEGER NOT NULL, " +
                "title TEXT NOT NULL, description TEXT NOT NULL, " +
                "coverImage TEXT NOT NULL, genre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+COMIC_CHAPTER_TABLE_NAME + " " +"(CCID INTEGER PRIMARY KEY AUTOINCREMENT, CID INTEGER NOT NULL, " +
                "title TEXT NOT NULL, " +
                "FOREIGN KEY(CID) REFERENCES COMIC_TABLE_NAME (CID))");

        db.execSQL("CREATE TABLE "+COMIC_SCENE_TABLE_NAME + " " +"(CSID INTEGER PRIMARY KEY AUTOINCREMENT, CCID INTEGER NOT NULL, " +
                "title TEXT NOT NULL, " +
                "FOREIGN KEY(CCID) REFERENCES COMIC_CHAPTER_TABLE_NAME (CCID))");


        db.execSQL("CREATE TABLE "+IMAGE_TABLE_NAME + " " +"(CIID INTEGER PRIMARY KEY AUTOINCREMENT, CSID INTEGER NOT NULL, " +
                "image TEXT NOT NULL, tag TEXT NOT NULL, " +
                "leftM INTEGER NOT NULL, topM INTEGER NOT NULL, FOREIGN KEY(CSID) REFERENCES COMIC_SCENE_TABLE_NAME (CSID))");

        db.execSQL("CREATE TABLE "+DIALOG_TABLE_NAME + " " +"(CDID INTEGER PRIMARY KEY AUTOINCREMENT, CSID INTEGER NOT NULL, " +
                "dialog TEXT NOT NULL, tag TEXT NOT NULL, " +
                "leftM INTEGER NOT NULL, topM INTEGER NOT NULL, FOREIGN KEY(CSID) REFERENCES COMIC_SCENE_TABLE_NAME (CSID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(("DROP TABLE IF EXISTS " +TEXT_TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +TEXT_CHAPTER_TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +TEXT_PAGE_TABLE_NAME));



        db.execSQL(("DROP TABLE IF EXISTS " +COMIC_TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +COMIC_CHAPTER_TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +COMIC_SCENE_TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +IMAGE_TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +DIALOG_TABLE_NAME));

        onCreate(db);
    }

    public long addTextStory(TextStory textStory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("title",textStory.getTitle());
        contentvalues.put("description",textStory.getDescription());
        contentvalues.put("coverImage",textStory.getCoverImage());
        contentvalues.put("genre", textStory.getGenre());
        contentvalues.put("UID",textStory.getUID());
        long res = db.insert(TEXT_TABLE_NAME,null,contentvalues);
        return res;
    }

    public long addTextChapter(TextChapter textChapter,Integer TID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("title",textChapter.getTitle());
        contentvalues.put("TID",TID);
        long res = db.insert(TEXT_CHAPTER_TABLE_NAME,null,contentvalues);
        return res;
    }
    public long addTextPage(TextPage textPage,Integer TCID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("story",textPage.getStory());
        contentvalues.put("TCID",TCID);
        long res = db.insert(TEXT_PAGE_TABLE_NAME,null,contentvalues);
        return res;
    }

    public long addComicStory(ComicStory comicStory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("title",comicStory.getTitle());
        contentvalues.put("description",comicStory.getDescription());
        contentvalues.put("coverImage",comicStory.getCoverImage());
        contentvalues.put("genre", comicStory.getGenre());
        contentvalues.put("UID", comicStory.getUID());
        long res = db.insert(COMIC_TABLE_NAME,null,contentvalues);
        //Integer id = getComicId(comicStory.getTitle(),comicStory.getDescription());
        return res;
    }

    public long updateComicStory(ComicStory comicStory, Integer CID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("title",comicStory.getTitle());
        contentvalues.put("description",comicStory.getDescription());
        contentvalues.put("coverImage",comicStory.getCoverImage());
        contentvalues.put("genre", comicStory.getGenre());
        long res = db.update(COMIC_TABLE_NAME,contentvalues,"CID="+CID,null);
        return res;

    }

    public long updateTextStory(TextStory textStory, Integer TID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("title",textStory.getTitle());
        contentvalues.put("description",textStory.getDescription());
        contentvalues.put("coverImage",textStory.getCoverImage());
        contentvalues.put("genre", textStory.getGenre());
        long res = db.update(TEXT_TABLE_NAME,contentvalues,"TID="+TID,null);
        return res;

    }

    public long addComicChapter(ComicChapter comicChapter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("title",comicChapter.getTitle());
        contentvalues.put("CID",comicChapter.getCID());
        long res = db.insert(COMIC_CHAPTER_TABLE_NAME,null,contentvalues);
        return res;
    }

    public long addComicScene(ComicScene comicScene){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("title",comicScene.getTitle());
        contentvalues.put("CCID",comicScene.getCCID());
        long res = db.insert(COMIC_SCENE_TABLE_NAME,null,contentvalues);
        return res;
    }

    public long addImage(ImageViewClass imageView){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("CSID", imageView.getCSID());
        contentvalues.put("image",imageView.getImg());
        contentvalues.put("tag",imageView.getTag());
        contentvalues.put("leftM",imageView.getLeftM());
        contentvalues.put("topM", imageView.getTopM());
        long res = db.insert(IMAGE_TABLE_NAME,null,contentvalues);
        System.out.println("added topMargin "+imageView.getTopM());
        System.out.println("added leftMargin "+imageView.getLeftM());
        return res;
    }

    public long updateImage(ImageViewClass imageViewClass, Integer tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("leftM",imageViewClass.getLeftM());
        contentvalues.put("topM", imageViewClass.getTopM());
        long res = db.update(IMAGE_TABLE_NAME,contentvalues,"tag="+tag,null);
        return res;

    }

    public long addDialog(DialogViewClass dialogView){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("dialog",dialogView.getImg());
        contentvalues.put("tag",dialogView.getTag());
        contentvalues.put("leftM",dialogView.getLeftM());
        contentvalues.put("topM", dialogView.getTopM());
        contentvalues.put("CSID", dialogView.getCSID());
        long res = db.insert(DIALOG_TABLE_NAME,null,contentvalues);
        return res;
    }

    public long updateDialog(DialogViewClass dialogViewClass, Integer tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("leftM",dialogViewClass.getLeftM());
        contentvalues.put("topM", dialogViewClass.getTopM());
        long res = db.update(DIALOG_TABLE_NAME,contentvalues,"tag="+tag,null);
        return res;

    }

    public long updateTextPage(TextPage textPage,Integer TPID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("story",textPage.getStory());
        long res = db.update(TEXT_PAGE_TABLE_NAME,contentvalues,"TPID="+TPID,null);
        return res;

    }

    public int getTextId(String title,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer textId = null;
        String selectQuery = " select * from "+TEXT_TABLE_NAME +" where title ==  '"+ title+"' AND description ==  '"
                + description+"'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Integer TID;
                TID = c.getInt(c.getColumnIndex("TID"));
                textId = TID;

            }while(c.moveToNext());
        }
        c.close();
        return textId;
    }

    public int getComicId(String title,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer textId = 0;
        String selectQuery = " select * from "+COMIC_TABLE_NAME +" where title ==  '"+ title+"' AND description ==  '"
                + description+"'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{

                textId = c.getInt(c.getColumnIndex("CID"));

            }while(c.moveToNext());
        }
        c.close();
        return textId;
    }

    public int getComicChapterId(Integer CID, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer id = null;
        String selectQuery = " select * from "+COMIC_CHAPTER_TABLE_NAME +" where title ==  '"+ title+"' AND CID ==  '"
                + CID+"'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Integer TID;
                TID = c.getInt(c.getColumnIndex("CCID"));
                id = TID;

            }while(c.moveToNext());
        }
        c.close();
        return id;
    }

    public int getTextChapterId(Integer TID, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer id = null;
        String selectQuery = " select * from "+TEXT_CHAPTER_TABLE_NAME +" where title ==  '"+ title+"' AND TID ==  '"
                + TID+"'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                id = c.getInt(c.getColumnIndex("TCID"));

            }while(c.moveToNext());
        }
        c.close();
        return id;
    }

    public int getComicSceneId(Integer CCID, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer id = null;
        String selectQuery = " select * from "+COMIC_SCENE_TABLE_NAME +" where title ==  '"+ title+"' AND CCID ==  '"
                + CCID+"'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{

                id = c.getInt(c.getColumnIndex("CSID"));

            }while(c.moveToNext());
        }
        c.close();
        return id;
    }

    public ArrayList<TextStory> getAllTextStory(Integer UID){
        ArrayList<TextStory> textStories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TEXT_TABLE_NAME +" where UID ==  '"+ UID+"'";
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                String description;
                String genre;
                String coverImage;
                Integer TID;
                title = c.getString(c.getColumnIndex("title"));
                description = c.getString(c.getColumnIndex("description"));
                genre = c.getString(c.getColumnIndex("genre"));
                coverImage = c.getString(c.getColumnIndex("coverImage"));
                TID = c.getInt(c.getColumnIndex("TID"));

                TextStory textStory = new TextStory(title, description, coverImage,genre);
                textStory.setTID(TID);
                textStories.add(textStory);

            }while(c.moveToNext());
        }
        c.close();
        return textStories;
    }

    public ArrayList<TextChapter> getAllTextChapter(Integer TID){
        ArrayList<TextChapter> textChapters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TEXT_CHAPTER_TABLE_NAME +" where TID ==  '"+ TID+"'";;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                Integer TCID;
                title = c.getString(c.getColumnIndex("title"));
                TCID = c.getInt(c.getColumnIndex("TCID"));

                TextChapter textChapter = new TextChapter(title);
                textChapter.setTCID(TCID);
                textChapter.setTID(TID);
                textChapters.add(textChapter);

            }while(c.moveToNext());
        }
        c.close();
        return textChapters;
    }

    public ArrayList<TextPage> getAllTextPage(Integer TCID){
        ArrayList<TextPage> textChapters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TEXT_PAGE_TABLE_NAME +" where TCID ==  '"+ TCID+"'";;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                Integer TPID;
                title = c.getString(c.getColumnIndex("story"));
                TPID = c.getInt(c.getColumnIndex("TPID"));

                TextPage textChapter = new TextPage(TCID,title);
                textChapter.setTPID(TPID);

                textChapters.add(textChapter);

            }while(c.moveToNext());
        }
        c.close();
        return textChapters;
    }

    public TextPage getPage(Integer TPID){
        TextPage textPage = new TextPage();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TEXT_PAGE_TABLE_NAME +" where TPID ==  '"+ TPID+"'";;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String story;
                Integer TCID;
                story = c.getString(c.getColumnIndex("story"));
                TCID = c.getInt(c.getColumnIndex("TCID"));
                textPage.setTCID(TCID);
                textPage.setStory(story);
                textPage.setTPID(TPID);

            }while(c.moveToNext());
        }
        c.close();


        return textPage;
    }

    public TextStory getStory(Integer TID){
        TextStory textStory = new TextStory();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TEXT_TABLE_NAME +" where TID ==  '"+ TID+"'";;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                Integer UID;
                String description,genre,coverImage;
                title = c.getString(c.getColumnIndex("title"));
                UID = c.getInt(c.getColumnIndex("UID"));
                description = c.getString(c.getColumnIndex("description"));
                genre = c.getString(c.getColumnIndex("genre"));
                coverImage = c.getString(c.getColumnIndex("coverImage"));

                textStory.setTID(TID);
                textStory.setUID(UID);
                textStory.setTitle(title);
                textStory.setCoverImage(coverImage);
                textStory.setDescription(description);
                textStory.setGenre(genre);


            }while(c.moveToNext());
        }
        c.close();


        return textStory;
    }

    public ComicStory getComic(Integer CID){
        ComicStory comicStory = new ComicStory();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + COMIC_TABLE_NAME +" where CID ==  '"+ CID+"'";;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                Integer UID;
                String description,genre,coverImage;
                title = c.getString(c.getColumnIndex("title"));
                UID = c.getInt(c.getColumnIndex("UID"));
                description = c.getString(c.getColumnIndex("description"));
                genre = c.getString(c.getColumnIndex("genre"));
                coverImage = c.getString(c.getColumnIndex("coverImage"));

                comicStory.setCID(CID);
                comicStory.setUID(UID);
                comicStory.setTitle(title);
                comicStory.setCoverImage(coverImage);
                comicStory.setDescription(description);
                comicStory.setGenre(genre);


            }while(c.moveToNext());
        }
        c.close();


        return comicStory;
    }

    public ArrayList<ComicStory> getAllComicStory(Integer UID){
        ArrayList<ComicStory> textStories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + COMIC_TABLE_NAME +" where UID ==  '"+ UID+"'";
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                String description;
                String genre;
                String coverImage;
                Integer TID;
                System.out.println("column index "+c.getColumnName(0)+c.getColumnName(1)+c.getColumnName(2)+c.getColumnName(3)+c.getColumnName(4)+c.getColumnName(5));
                title = c.getString(c.getColumnIndex("title"));
                description = c.getString(c.getColumnIndex("description"));
                genre = c.getString(c.getColumnIndex("genre"));
                coverImage = c.getString(c.getColumnIndex("coverImage"));
                TID = c.getInt(c.getColumnIndex("CID"));



                ComicStory textStory = new ComicStory(title, description, coverImage,genre);
                textStory.setCID(TID);
                textStory.setUID(UID);
                textStories.add(textStory);

            }while(c.moveToNext());
        }
        c.close();
        return textStories;
    }

    public ArrayList<ComicChapter> getAllComicChapter(Integer CID){
        ArrayList<ComicChapter> textChapters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + COMIC_CHAPTER_TABLE_NAME +" where CID ==  '"+ CID+"'";;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                Integer CCID;
                title = c.getString(c.getColumnIndex("title"));
                CCID = c.getInt(c.getColumnIndex("CCID"));

                ComicChapter textChapter = new ComicChapter(CID,title);
                textChapter.setCCID(CCID);
                textChapters.add(textChapter);

            }while(c.moveToNext());
        }
        c.close();
        return textChapters;
    }

    public ArrayList<ComicScene> getAllComicPage(Integer CCID){
        ArrayList<ComicScene> textChapters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + COMIC_SCENE_TABLE_NAME  +" where CCID ==  '"+ CCID+"'";
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String title;
                Integer CSID;
                title = c.getString(c.getColumnIndex("title"));
                CSID = c.getInt(c.getColumnIndex("CSID"));

                ComicScene textChapter = new ComicScene(CCID,title);
                textChapter.setCSID(CSID);

                textChapters.add(textChapter);

            }while(c.moveToNext());
        }
        c.close();
        return textChapters;
    }

    public ArrayList<ImageViewClass> getAllImages(Integer ID){
        ArrayList<ImageViewClass> images = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + IMAGE_TABLE_NAME +" where CSID ==  '"+ ID+"'";
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String image;
                Integer tag;
                Integer leftM;
                Integer topM;
                Integer CIID;
                image = c.getString(c.getColumnIndex("image"));
                tag = c.getInt(c.getColumnIndex("tag"));
                leftM = c.getInt(c.getColumnIndex("leftM"));
                topM = c.getInt(c.getColumnIndex("topM"));
                CIID = c.getInt(c.getColumnIndex("CIID"));

                ImageViewClass imageView = new ImageViewClass(image,leftM,topM,tag);
                imageView.setCSID(ID);
                imageView.setCIID(CIID);
                images.add(imageView);

            }while(c.moveToNext());
        }
        c.close();
        return images;
    }

    public ArrayList<DialogViewClass> getAllDialog(Integer ID){
        ArrayList<DialogViewClass> dialogs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + DIALOG_TABLE_NAME +" where CSID ==  '"+ ID+"'";;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String dialog;
                Integer tag;
                Integer leftM;
                Integer topM;
                Integer CDID;
                Integer CSID;
                dialog = c.getString(c.getColumnIndex("dialog"));
                tag = c.getInt(c.getColumnIndex("tag"));
                leftM = c.getInt(c.getColumnIndex("leftM"));
                topM = c.getInt(c.getColumnIndex("topM"));
                CDID = c.getInt(c.getColumnIndex("CDID"));
                CSID = c.getInt(c.getColumnIndex("CSID"));

                DialogViewClass dialogViewClass = new DialogViewClass(CSID,dialog,leftM,topM,tag);
                dialogViewClass.setCDID(CDID);
                dialogs.add(dialogViewClass);

            }while(c.moveToNext());
        }
        c.close();
        return dialogs;
    }
}
