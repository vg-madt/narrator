package com.narrator.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.narrator.entity.ChapterPage;
import com.narrator.entity.ComicChapter;
import com.narrator.entity.ComicDialog;
import com.narrator.entity.ComicFavourites;
import com.narrator.entity.ComicImage;
import com.narrator.entity.ComicScene;
import com.narrator.entity.ComicStory;
import com.narrator.entity.TextChapter;
import com.narrator.entity.TextFavourites;
import com.narrator.entity.TextStory;
import com.narrator.entity.Token;
import com.narrator.entity.User;
import com.narrator.repo.ChapterPageRepo;
import com.narrator.repo.ComicChapterRepo;
import com.narrator.repo.ComicDialogRepo;
import com.narrator.repo.ComicFavouritesRepo;
import com.narrator.repo.ComicImageRepo;
import com.narrator.repo.ComicSceneRepo;
import com.narrator.repo.ComicStoryRepo;
import com.narrator.repo.TextChapterRepo;
import com.narrator.repo.TextFavouritesRepo;
import com.narrator.repo.TextStoryRepo;
import com.narrator.repo.TokenRepo;
import com.narrator.repo.UserRepo;
import com.narrator.util.PayloadUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractController {

    private static final Map<Type, JpaRepository> REPOS = new HashMap<>();

    protected AbstractController(
            final UserRepo ur,
            final TokenRepo tr,
            final TextStoryRepo tsr,
            final TextChapterRepo tcr,
            final TextFavouritesRepo tfr,
            final ChapterPageRepo cpr,
            final ComicStoryRepo csr,
            final ComicSceneRepo cscr,
            final ComicImageRepo cir,
            final ComicDialogRepo cdr,
            final ComicFavouritesRepo cfr,
            final ComicChapterRepo ccr) {
        REPOS.put(Type.USER, ur);
        REPOS.put(Type.TOKEN, tr);
        REPOS.put(Type.TEXT_STORY, tsr);
        REPOS.put(Type.TEXT_CHAPTER, tcr);
        REPOS.put(Type.TEXT_FAVOURITES, tfr);
        REPOS.put(Type.CHPATER_PAGE, cpr);
        REPOS.put(Type.COMIC_STORY, csr);
        REPOS.put(Type.COMIC_CHAPTER, ccr);
        REPOS.put(Type.COMIC_SCENE, cscr);
        REPOS.put(Type.COMIC_IMAGE, cir);
        REPOS.put(Type.COMIC_DIALOG, cdr);
        REPOS.put(Type.COMIC_FAVOURITES, cfr);
    }

    protected <T, R extends JpaRepository<T, Integer>> R getRepo(final Type type, final Class<R> r, final Class<T> t) {
        return (R) REPOS.get(type);
    }

    protected Class getType(final String entity) {
        return Type.find(entity).clazz;
    }

    protected <T> T saveOrUpdate(final String entity, final String payload) {
    	log.debug("persisting entity {}",payload);
        final T t = (T) PayloadUtil.request(payload, getType(entity));
        final Class<T> clazz = (Class<T>) t.getClass();
        final Type type = Type.find(clazz);
        final JpaRepository repo = REPOS.get(type);
        return (T) repo.save(t);
    }

    protected <T> List<T> list(final String entity) {
        final Type type = Type.find(entity);
        final JpaRepository repo = REPOS.get(type);
        return repo.findAll();
    }

    protected <T> T get(final String entity, final Integer id) {
        final Type type = Type.find(entity);
        final JpaRepository repo = REPOS.get(type);
        return (T) repo.findById(id).get();
    }

    protected enum Type {
        USER("user", User.class),
        TOKEN("token", Token.class),
        TEXT_STORY("textStory", TextStory.class),
        TEXT_CHAPTER("textChapter", TextChapter.class),
        CHPATER_PAGE("chapterPage", ChapterPage.class),
        TEXT_FAVOURITES("textFavourites", TextFavourites.class),
        COMIC_STORY("comicStory", ComicStory.class),
        COMIC_SCENE("comicScene", ComicScene.class),
        COMIC_IMAGE("comicImage", ComicImage.class),
        COMIC_DIALOG("comicDialog", ComicDialog.class),
        COMIC_FAVOURITES("comicFavourites", ComicFavourites.class),
        COMIC_CHAPTER("comicChapter", ComicChapter.class);

        private final String type;
        private final Class clazz;

        private Type(final String type, final Class clazz) {
            this.type = type;
            this.clazz = clazz;
        }

        protected static Type find(final String type) {
            final Type[] values = Type.values();
            for (final Type value : values) {
                if (value.type.equals(type)) {
                    return value;
                }
            }
            return null;
        }

        protected static Type find(final Class clazz) {
            final Type[] values = Type.values();
            for (final Type value : values) {
                if (value.clazz.equals(clazz)) {
                    return value;
                }
            }
            return null;
        }

    }

}
