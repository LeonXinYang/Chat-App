package com.example.group_project;

import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PostDataXML {
    List<Post> posts;
    List<Post> posts_public;
    static PostDataXML postDataXML; // SingleTon Design Pattern
    static AssetManager assetManager;

    public PostDataXML(List<Post> posts) {
        this.posts = posts;
    }

    //DESIGN PATTERN METHOD USED IN HERE. Singleton Design pattern method;
    public static PostDataXML getPostDataXMLInstance(){
        if(postDataXML == null){
            postDataXML = new PostDataXML();
        }
        return postDataXML;
    }

    public PostDataXML() {
        posts = new ArrayList<Post>();
        posts_public = new ArrayList<Post>();
    }

    public void setAssetManager(AssetManager am){
        assetManager = am;
    }
    /**
     * Reference: We refer some codes from the Lecture Material (Code shown in the lecture)
     * @return List<Post>, a list of post in the stored file.
     */
    public List<Post> loadData(){

        DocumentBuilderFactory documentBuildFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentBuildFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(assetManager.open("posts.xml"));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Post");

            for(int i = 0; i < nodeList.getLength(); i++)
            {
                Node n = nodeList.item(i);
                ArrayList<String> tags = new ArrayList<>();
                ArrayList<Integer> like = new ArrayList<>();
                if(n.getNodeType() == Node.ELEMENT_NODE) {
                    Element element		= (Element) n;
                    Integer postId 			= Integer.parseInt(element.getAttribute("id"));
                    Integer userID 	= Integer.parseInt(element.getElementsByTagName("UserID").item(0).getTextContent());
                    String date 	= element.getElementsByTagName("Date").item(0).getTextContent();
                    String content 	= element.getElementsByTagName("Content").item(0).getTextContent();
                    String originTag 	= element.getElementsByTagName("Tag").item(0).getTextContent();
                    String originLike	= element.getElementsByTagName("LikeThisPost").item(0).getTextContent();
                    String isPublic  = element.getElementsByTagName("isPublic").item(0).getTextContent();
                    String view  = element.getElementsByTagName("View").item(0).getTextContent();
                    String media  = element.getElementsByTagName("Media").item(0).getTextContent();

                    String[] tagList = originTag.split(",");
                    for(String each : tagList){
                        if(each.equals("")== false && each != null) {
                            tags.add(each);
                        }
                    }
                    String[] likeList = originLike.split(",");
                    for(String each : likeList){
                        if(each.equals("")== false && each != null) {
                            like.add(Integer.parseInt(each));
                        }
                    }

                    Post p 			= new Post(postId, userID, date, content, tags, like, Boolean.valueOf(isPublic), Integer.parseInt(view), media);
                    if (p.getPublic()){
                        posts_public.add(p);
                    }
                    posts.add(p);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Reference: We refer some codes from the Lecture Material (Code shown in the lecture)
     * @param OutputPath
     */
    public void savaData(String OutputPath){
        //TODO
        File file = new File(OutputPath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("posts");
            document.appendChild(rootElement);
            for(Post post : posts)
            {
                Element postElement = document.createElement("Post");
                postElement.setAttribute("id", Integer.toString(post.getPostID()));

                Element UserIDElement = document.createElement("UserID");
                UserIDElement.appendChild(document.createTextNode(post.getUserID().toString()));
                postElement.appendChild(UserIDElement);

                Element date = document.createElement("Date");
                date.appendChild(document.createTextNode(post.getDate()));
                postElement.appendChild(date);

                Element content = document.createElement("Content");
                content.appendChild(document.createTextNode(post.getContent()));
                postElement.appendChild(content);

                Element tag = document.createElement("Tag");
                ArrayList<String> tags = post.getTags();
                String tagsOut = "";
                if(tags.size() != 0) {
                    for (String each : tags) {
                        tagsOut += each + ",";
                    }
                    tagsOut = tagsOut.substring(0, tagsOut.length()-1);
                }
                tag.appendChild(document.createTextNode(tagsOut));
                postElement.appendChild(tag);

                Element like = document.createElement("LikeThisPost");
                ArrayList<Integer> likeUser = post.getLikeThisPostUsers();
                String likeOut = "";
                if(likeUser.size() != 0) {
                    for (Integer each : likeUser) {
                        likeOut += each.toString() + ",";
                    }
                    likeOut = likeOut.substring(0, likeOut.length()-1);
                }
                like.appendChild(document.createTextNode(likeOut));
                postElement.appendChild(like);

                rootElement.appendChild(postElement);

                Element isPublic = document.createElement("isPublic");
                isPublic.appendChild(document.createTextNode(post.getPublic().toString()));
                postElement.appendChild(isPublic);

                Element view = document.createElement("View");
                view.appendChild(document.createTextNode(post.getView().toString()));
                postElement.appendChild(view);

                Element media = document.createElement("Media");
                media.appendChild(document.createTextNode(post.getMedia()));
                postElement.appendChild(media);
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //This method is to compare the post publish date, so that we can order them
    // (Helper Function of orderTheRecentPost
    public static boolean isDateLatest(String date1, String date2){
        try {
            Integer dateOne = Integer.parseInt(date1);
            Integer dateTwo = Integer.parseInt(date2);
            if(dateOne >= dateTwo){
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            System.out.print("date formate wrong!");
            throw e;
        }
    }

    //Show the most recent post; ！！！！！！！！！！！！
    public void orderTheRecentPost(){
        PostDataXML singleton = PostDataXML.getPostDataXMLInstance();
        PostDataXML replace = new PostDataXML();
        for(Post each : singleton.posts){
            replace.posts.add(each);
        }
        PostDataXML output = new PostDataXML();
        while(replace.posts.size() != 0){
            Post mostRecent = PostDataXML.getMostRecent(replace.posts);
            output.posts.add(mostRecent);
            replace.posts.remove(mostRecent);
        }
        PostDataXML.postDataXML = output;
        return;

    }

    //Helper Function of orderTheRecentPost
    public static Post getMostRecent(List<Post> posts){
        Post recent = null;
        String date = null;
        for(Post each : posts){
            if(recent == null){
                recent = each;
                date = each.getDate();
            }
            else{
                if(isDateLatest(each.getDate(), date)){
                    recent = each;
                    date = each.getDate();
                }
            }
        }
        return recent;
    }

    //Show the following users' post ！！！！！！！！！！！！！核心方法
    public static List<Post> showFollowerPost(List<Integer> follower){
        List<Post> output = new ArrayList<>();
        for(Post each : GlobalData.getPosts()){
            if(follower.contains(each.getUserID())){
                output.add(each);
            }
        }
        return output;
    }

    public static int getPostsLike(Integer id){
        int sum=0;
        for(Post each : GlobalData.getPosts()){
            if(each.getUserID().equals(id)){sum = sum+each.getLikeThisPostUsers().size();}
        }
        return sum;
       }

    public int getViews(Integer id){
        int sum=0;
        PostDataXML singleton = PostDataXML.getPostDataXMLInstance();
        PostDataXML replace = new PostDataXML();
        for(Post each : singleton.posts){
            if(each.getUserID() ==id){sum=sum+each.getView();}
        }
        return sum;
       }

    public static void SetUserFollow(User user, Post post){
        for(Post each : GlobalData.getPosts()){
            if(each.getPostID().equals(post.getPostID())){each.addLikedUser(user.getUserID());}
        }
    }
    }


