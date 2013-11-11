package com.fourpool.goodreads.android.recentupdates;

import com.fourpool.goodreads.android.model.Actor;
import com.fourpool.goodreads.android.model.Update;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RecentUpdatesParser {
    public List<Update> parse(InputStream updatesInputStream) throws ParserConfigurationException, IOException, SAXException {
        List<Update> updates = new ArrayList<Update>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(updatesInputStream);

        NodeList nodeList = doc.getElementsByTagName("updates").item(0).getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node updateNode = nodeList.item(i);

            if (updateNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            String updateType = ((Element) updateNode).getAttribute("type");
            String imageUrl = null;
            Actor actor = null;

            NodeList updateElementChildren = updateNode.getChildNodes();
            for (int j = 0; j < updateElementChildren.getLength(); j++) {
                Node updateChild = updateElementChildren.item(j);

                if (updateChild.getNodeName().equals("image_url")) {
                    imageUrl = updateChild.getLastChild().getTextContent().trim();
                } else if (updateChild.getNodeName().equals("actor")) {
                    NodeList actorChildNodes = updateChild.getChildNodes();

                    int id = -1;
                    String name = null;
                    String actorImageUrl = null;

                    for (int k = 0; k < actorChildNodes.getLength(); k++) {
                        Node actorProperty = actorChildNodes.item(k);

                        if (actorProperty.getNodeName().equals("id")) {
                            id = Integer.parseInt(actorProperty.getLastChild().getTextContent());
                        } else if (actorProperty.getNodeName().equals("name")) {
                            name = actorProperty.getLastChild().getTextContent();
                        } else if (actorProperty.getNodeName().equals("image_url")) {
                            actorImageUrl = actorProperty.getLastChild().getTextContent();
                        }
                    }

                    actor = new Actor(id, name, actorImageUrl);
                }
            }

            Update update = new Update(updateType, imageUrl, actor);
            updates.add(update);
        }

        return updates;
    }
}
