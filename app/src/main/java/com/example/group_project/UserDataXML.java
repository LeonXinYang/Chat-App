package com.example.group_project;

import android.content.res.AssetManager;
import android.service.autofill.UserData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UserDataXML {
    static List<User> users;
    static UserDataXML userDataXML; // SingleTon Design Pattern
    static AssetManager assetManager;

    public UserDataXML() {
        users = new ArrayList<User>();
    }

    public UserDataXML(List<User> users) {
        this.users = users;
    }

    //DESIGN PATTERN METHOD USED IN HERE. Singleton Design pattern method;
    public static UserDataXML getUserDataXMLInstance(){
        if(userDataXML == null){
            userDataXML = new UserDataXML();
        }
        return userDataXML;
    }

    public void setAssetManager(AssetManager am){
        assetManager = am;
    }

    public void loadData(){

        DocumentBuilderFactory documentBuildFactory = DocumentBuilderFactory.newInstance();

        try{

            DocumentBuilder documentBuilder = documentBuildFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(assetManager.open("users.xml"));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("User");


            // looping all users
            for (int i = 0; i<nodeList.getLength();i++){
                Node userNode = nodeList.item(i);
                if(userNode.getNodeType()==Node.ELEMENT_NODE){
                    NodeList this_user_attributes = userNode.getChildNodes();
                    User this_user = new User();

                    // create arraylists to contain the data
                    ArrayList<Integer> fans_list = new ArrayList<>();
                    ArrayList<Integer> posts_list = new ArrayList<>();
                    ArrayList<Integer> following_list = new ArrayList<>();

                    // for all attributes of user data, read from xml and store in memory
                    for (int j=0;j<this_user_attributes.getLength();j++){
                        Node n = this_user_attributes.item(j);
                        String nodeName = n.getNodeName();
                        if (nodeName.equals("ID")){
                            Node contentNode = n.getFirstChild();
                            this_user.userID = Integer.valueOf(contentNode.getTextContent());
                        }  else if (nodeName.equals("Username")) {
                            Node contentNode = n.getFirstChild();
                            this_user.userName = contentNode.getTextContent();
                        } else if (nodeName.equals("IsProfilePublic")) {
                            Node contentNode = n.getFirstChild();
                            String isPublic = contentNode.getTextContent();
                            this_user.isProfilePublic = Boolean.valueOf(isPublic);
                        }
                        else if (nodeName.equals("Fans")) {
                            Node contentNode = n.getFirstChild();
                            if(contentNode != null) {
                                String[] fans = contentNode.getTextContent().split(",");
                                for (String each : fans) {
                                    fans_list.add(Integer.parseInt(each));
                                }
                            }
                        } else if (nodeName.equals("Posts")){
                            Node contentNode = n.getFirstChild();
                            if(contentNode != null) {
                                String[] posts = contentNode.getTextContent().split(",");
                                for (String each : posts) {
                                    posts_list.add(Integer.parseInt(each));
                                }
                            }
                        } else if (nodeName.equals("Following")) {
                            Node contentNode = n.getFirstChild();
                            if(contentNode != null) {
                                String[] followings = contentNode.getTextContent().split(",");
                                for (String each : followings) {
                                    following_list.add(Integer.parseInt(each));
                                }
                            }
                        }
                    }

                    // Create users
                    this_user.fans = fans_list;
                    this_user.allPosts = posts_list;
                    this_user.following = following_list;
                    this_user = new User(this_user.userID,this_user.userName,
                            this_user.fans,this_user.allPosts,this_user.following,this_user.isProfilePublic);
                    users.add(this_user);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public void savaData(String filePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        File file = new File(filePath);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element docNode = doc.createElement("USERS");
            doc.appendChild(docNode);

            for (User user : this.users) {
                // Set elements
                Element user_element = doc.createElement("User");

                // transferring user attributes into elements
                Element ID_element = doc.createElement("ID");
                Element password_element = doc.createElement("Password");
                Element username_element = doc.createElement("Username");
                Element fans_element = doc.createElement("Fans");
                Element posts_element = doc.createElement("Posts");
                Element following_element = doc.createElement("Following");
                Element isPublic_element = doc.createElement("IsProfilePublic");

                ID_element.appendChild(doc.createTextNode(String.valueOf(user.getUserID())));
                username_element.appendChild(doc.createTextNode(user.getUserName()));
                isPublic_element.appendChild(doc.createTextNode(user.isProfilePublic.toString()));

                for (Integer fans_IDs : user.fans){
                    fans_element.appendChild(doc.createTextNode(String.valueOf(fans_IDs) + ","));
                }
                for (Integer post_IDs : user.allPosts){
                    posts_element.appendChild(doc.createTextNode(String.valueOf(post_IDs)+","));
                }
                for (Integer following_IDs : user.following){
                    following_element.appendChild(doc.createTextNode(String.valueOf(following_IDs)+","));
                }

                // assign elements to be child of the parent
                user_element.appendChild(ID_element);
                user_element.appendChild(username_element);
                user_element.appendChild(fans_element);
                user_element.appendChild(posts_element);
                user_element.appendChild(following_element);
                user_element.appendChild(isPublic_element);

                // add user element to docNode
                docNode.appendChild(user_element);
            }
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();

            tf.setOutputProperty(OutputKeys.ENCODING,"utf-8");
            tf.setOutputProperty(OutputKeys.INDENT,"yes");

            DOMSource source = new DOMSource(doc);
            StreamResult output = new StreamResult(file);
            tf.transform(source,output);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public Integer getFans(Integer userid){
        for(User each : users){
            if(each.getUserID()==userid){
                return each.getFollowers().size();
            }
        }
        return 0;
    }

    public static void SetFollows(Integer userAid, Integer userBid){
        for(User each : users){
            if(each.getUserID()==userAid){
                each.addUserFollowing(userBid);
            }else if(each.getUserID()==userBid){
                each.addUserFans(userAid);
            }
        }
    }

//    public static void main(String[] args) {
//        add_fans_follow();
//    }
//
//    public static void add_fans_follow(){
//        UserDataXML userDataXML = UserDataXML.getUserDataXMLInstance();
//        userDataXML.loadData();
//        List<User> users = userDataXML.users;
//        Random rand_follow = new Random();
//        int target_num = rand_follow.nextInt(20);
//        for (int i = 0; i < target_num; i++){
//            Random rand_user = new Random();
//            int userA = rand_user.nextInt(users.size());
//            int userB = rand_user.nextInt(users.size());
//            if (userA!=userB){
//                SetFollows(userA,userB);
//            }
//        }
//        userDataXML.savaData("src/test/java/com/example/group_project/users.xml");
//    }

}
