package com.lgh.huanlebian.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebIndexContentListModel {
   private String title;
   private String imageUrl;
   private String url;
   private List<WebNewsListModel> newsList;
}
