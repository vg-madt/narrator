package com.narrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.narrator.entity.ChapterPage;
import com.narrator.entity.ComicChapter;
import com.narrator.entity.ComicDialog;
import com.narrator.entity.ComicImage;
import com.narrator.entity.ComicScene;
import com.narrator.entity.ComicStory;
import com.narrator.entity.TextChapter;
import com.narrator.entity.TextStory;
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

@RestController
public class NarratorController extends AbstractController {

    public NarratorController(
            @Autowired
            final UserRepo userRepo,
            @Autowired
            final TokenRepo tokenRepo,
            @Autowired
            final TextStoryRepo textStoryRepo,
            @Autowired
            final TextFavouritesRepo textFavouritesRepo,
            @Autowired
            final TextChapterRepo textChapterRepo,
            @Autowired
            final ComicStoryRepo comicStoryRepo,
            @Autowired
            final ComicSceneRepo comicSceneRepo,
            @Autowired
            final ComicImageRepo comicImageRepo,
            @Autowired
            final ComicDialogRepo comicDialogRepo,
            @Autowired
            final ComicFavouritesRepo comicFavouritesRepo,
            @Autowired
            final ComicChapterRepo comicChapterRepo,
            @Autowired
            final ChapterPageRepo chapterPageRepo) {
        super(userRepo, tokenRepo, textStoryRepo, textChapterRepo, textFavouritesRepo, chapterPageRepo, comicStoryRepo,
                comicSceneRepo, comicImageRepo,comicDialogRepo, comicFavouritesRepo, comicChapterRepo);
    }

    @RequestMapping(value="narrator/user/email/{email}", produces={"application/json"})
    public String userByEmail(@PathVariable("email") final String email) {
        return PayloadUtil.response(getRepo(Type.USER, UserRepo.class, User.class).findByEmail(email));
    }
    
    /*@RequestMapping(value="narrator/user/{userId}", produces={"application/json"})
    public String userByUserId(@PathVariable("userId") final Integer userId) {
        return PayloadUtil.response(getRepo(Type.USER, UserRepo.class, User.class).findByUserId(userId));
    }*/
    
    @RequestMapping(value="narrator/user/email/{email}/{password}", produces={"application/json"})
    public String userByEmail(@PathVariable("email") final String email,@PathVariable("password") final String password) {
        return PayloadUtil.response(getRepo(Type.USER, UserRepo.class, User.class).findByEmailAndPassword(email,password));
    }

    @RequestMapping(value="narrator/user/{userId}/textStory/list", produces={"application/json"})
    public String textStories(@PathVariable("userId") final Integer userId) {
        return PayloadUtil.response(getRepo(Type.TEXT_STORY, TextStoryRepo.class, TextStory.class).findByUid(userId));
    }

    @RequestMapping(value="narrator/user/{userId}/textStory/favourites", produces={"application/json"})
    public String textFavourites(@PathVariable("userId") final Integer userId) {
        return PayloadUtil.response(getRepo(Type.TEXT_STORY, TextStoryRepo.class, TextStory.class).findFavouritesByUid(userId));
    }

    /*@RequestMapping(value="narrator/user/{userId}/textStory/find/title/{search}", produces={"application/json"})
    public String textTitleSearch(@PathVariable("userId") final Integer userId,
            @PathVariable("search") final String search) {
        return PayloadUtil.response(getRepo(Type.TEXT_STORY, TextStoryRepo.class, TextStory.class).findByTitleContaining(search));
    }

    @RequestMapping(value="narrator/user/{userId}/textStory/find/description/{search}", produces={"application/json"})
    public String textDescriptionSearch(@PathVariable("userId") final Integer userId,
            @PathVariable("search") final String search) {
        return PayloadUtil.response(getRepo(Type.TEXT_STORY, TextStoryRepo.class, TextStory.class).findByDescriptionContaining(search));
    }

    @RequestMapping(value="narrator/user/{userId}/textStory/find/genre/{search}", produces={"application/json"})
    public String textGenreSearch(@PathVariable("userId") final Integer userId,
            @PathVariable("search") final String search) {
        return PayloadUtil.response(getRepo(Type.TEXT_STORY, TextStoryRepo.class, TextStory.class).findByGenreContaining(search));
    }*/

    @RequestMapping(value="narrator/user/{userId}/textStory/{storyId}/chapters", produces={"application/json"})
    public String textChapters(@PathVariable("userId") final Integer userId,
            @PathVariable("storyId") final Integer storyId) {
        return PayloadUtil.response(getRepo(Type.TEXT_CHAPTER, TextChapterRepo.class, TextChapter.class).findByTid(storyId));
    }

    @RequestMapping(value="narrator/user/{userId}/textStory/{storyId}/chapter/{chapterId}/pages", produces={"application/json"})
    public String textChapterPages(@PathVariable("userId") final Integer userId,
            @PathVariable("storyId") final Integer storyId,
            @PathVariable("chapterId") final Integer chapterId) {
        return PayloadUtil.response(getRepo(Type.CHPATER_PAGE, ChapterPageRepo.class, ChapterPage.class).findByTcid(chapterId));
    }

    @RequestMapping(value="narrator/user/{userId}/comicStory/list", produces={"application/json"})
    public String comicStories(@PathVariable("userId") final Integer userId) {
        return PayloadUtil.response(getRepo(Type.COMIC_STORY, ComicStoryRepo.class, ComicStory.class).findByUid(userId));
    }

    @RequestMapping(value="narrator/user/{userId}/comicStory/favourites", produces={"application/json"})
    public String comicFavourites(@PathVariable("userId") final Integer userId) {
        return PayloadUtil.response(getRepo(Type.COMIC_STORY, ComicStoryRepo.class, ComicStory.class).findFavouritesByUid(userId));
    }

    /*@RequestMapping(value="narrator/user/{userId}/comicStory/find/title/{search}", produces={"application/json"})
    public String comicTitleSearch(@PathVariable("userId") final Integer userId,
            @PathVariable("search") final String search) {
        return PayloadUtil.response(getRepo(Type.COMIC_STORY, ComicStoryRepo.class, ComicStory.class).findByTitleContaining(search));
    }

    @RequestMapping(value="narrator/user/{userId}/comicStory/find/description/{search}", produces={"application/json"})
    public String comicDescriptionSearch(@PathVariable("userId") final Integer userId,
            @PathVariable("search") final String search) {
        return PayloadUtil.response(getRepo(Type.COMIC_STORY, ComicStoryRepo.class, ComicStory.class).findByDescriptionContaining(search));
    }

    @RequestMapping(value="narrator/user/{userId}/comicStory/find/genre/{search}", produces={"application/json"})
    public String comicGenreSearch(@PathVariable("userId") final Integer userId,
            @PathVariable("search") final String search) {
        return PayloadUtil.response(getRepo(Type.COMIC_STORY, ComicStoryRepo.class, ComicStory.class).findByGenreContaining(search));
    }*/

    @RequestMapping(value="narrator/user/{userId}/comicStory/{storyId}/chapters", produces={"application/json"})
    public String comicChapters(@PathVariable("userId") final Integer userId,
            @PathVariable("storyId") final Integer storyId) {
        return PayloadUtil.response(getRepo(Type.COMIC_CHAPTER, ComicChapterRepo.class, ComicChapter.class).findByCid(storyId));
    }

    @RequestMapping(value="narrator/user/{userId}/comicStory/{storyId}/chapter/{chapterId}/scenes", produces={"application/json"})
    public String comicScenes(@PathVariable("userId") final Integer userId,
            @PathVariable("storyId") final Integer storyId,
            @PathVariable("chapterId") final Integer chapterId) {
        return PayloadUtil.response(getRepo(Type.COMIC_SCENE, ComicSceneRepo.class, ComicScene.class).findByCcid(chapterId));
    }

    @RequestMapping(value="narrator/user/{userId}/comicStory/{storyId}/chapter/{chapterId}/scene/{sceneId}/images", produces={"application/json"})
    public String comicImages(@PathVariable("userId") final Integer userId,
            @PathVariable("storyId") final Integer storyId,
            @PathVariable("chapterId") final Integer chapterId,
            @PathVariable("sceneId") final Integer sceneId) {
        return PayloadUtil.response(getRepo(Type.COMIC_IMAGE, ComicImageRepo.class, ComicImage.class).findByCsid(sceneId));
    }
    
    @RequestMapping(value="narrator/user/{userId}/comicStory/{storyId}/chapter/{chapterId}/scene/{sceneId}/dialogs", produces={"application/json"})
    public String comicDialogs(@PathVariable("userId") final Integer userId,
            @PathVariable("storyId") final Integer storyId,
            @PathVariable("chapterId") final Integer chapterId,
            @PathVariable("sceneId") final Integer sceneId){
        return PayloadUtil.response(getRepo(Type.COMIC_DIALOG, ComicDialogRepo.class, ComicDialog.class).findByCsid(sceneId));
    }

    @RequestMapping(value="narrator/{entity}/write", produces={"application/json"}, consumes={"application/json"})
    public String write(@PathVariable("entity") final String entity,
            @RequestBody final String payload) {
        return PayloadUtil.response(saveOrUpdate(entity, payload));
    }

    @RequestMapping(value="narrator/{entity}/get/{id}", produces={"application/json"})
    public String entity(@PathVariable("entity") final String entity,
            @PathVariable("id") final Integer id) {
        return PayloadUtil.response(super.get(entity, id));
    }

    @RequestMapping(value="narrator/{entity}/list", produces={"application/json"})
    public String entities(@PathVariable("entity") final String entity) {
        return PayloadUtil.response(super.list(entity));
    }

}
