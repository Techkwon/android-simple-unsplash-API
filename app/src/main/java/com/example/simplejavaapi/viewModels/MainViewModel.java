package com.example.simplejavaapi.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.simplejavaapi.models.SearchImage;
import com.example.simplejavaapi.models.UnsplashImage;
import com.example.simplejavaapi.utils.APIClient;
import com.example.simplejavaapi.utils.APIInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private APIInterface api;
    private List<UnsplashImage> imageList = new ArrayList<>();
    private MutableLiveData<List<UnsplashImage>> liveImages;
    private int BASIC_PAGE;
    private int SEARCH_PAGE;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void initViewModel(){
        if(liveImages == null){
            liveImages = new MutableLiveData<>();
        }
        api = APIClient.getClient().create(APIInterface.class);
        getBasicImages();
    }


    private void getBasicImages(){
        BASIC_PAGE = 0;
        Observable<List<UnsplashImage>> observable = api.getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        CompositeDisposable cp = new CompositeDisposable();
        cp.add(observable.subscribe(images -> {
            imageList = images;
            liveImages.postValue(imageList);
        }));

        BASIC_PAGE ++;
    }

    public void getBasicPagination(){
        Observable<List<UnsplashImage>> observable = api.getNextPhotos(BASIC_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        CompositeDisposable cp = new CompositeDisposable();
        cp.add(observable.subscribe(images -> {
            imageList.addAll(images);
            liveImages.postValue(imageList);
        }));
        BASIC_PAGE ++;
    }


    public void getSearchImages(String query){
        SEARCH_PAGE = 0;
        Observable<SearchImage> observable = api.searchPhotos(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        CompositeDisposable cp = new CompositeDisposable();
        cp.add(observable.subscribe(images -> {
            imageList.clear();
            imageList.addAll(images.getImageList());
            liveImages.postValue(imageList);

            for(UnsplashImage image : imageList){
                Log.d("ViewModel:", image.getUrls().getThumb());
            }
        }));
        SEARCH_PAGE ++;
    }

    public void getSearchPagination(){
//        Observable<SearchImage> observable = api.ge

        SEARCH_PAGE ++;
    }

    public LiveData<List<UnsplashImage>> getLiveImages(){
        return liveImages;
    }
}
