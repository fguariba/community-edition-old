/*
 * Copyright (C) 2009-2010 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have received a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */

package org.alfresco.repo.transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.transfer.NodeFilter;
import org.alfresco.service.cmr.transfer.NodeFinder;
import org.alfresco.service.cmr.transfer.TransferService;

/**
 * This class can be used to build a set of node references from a given starting point. The caller can provide a list
 * of {@link NodeFinder} objects and a list of {@link NodeFilter} objects. Starting with the nodes supplied by the
 * caller, the crawler uses the NodeFinder objects to find other nodes. Each node that is found is then passed to the
 * NodeFilter objects to determine whether it should be included or ignored. Any included nodes are then fed back into
 * the NodeFinder objects to continue the crawl. This class was originally written to assist users of the
 * {@link TransferService} in combination with the {@link ChildAssociatedNodeFinder} and the {@link ContentClassFilter}.
 * 
 * @author brian
 * 
 */
public class StandardNodeCrawlerImpl
{
    private ServiceRegistry serviceRegistry;
    private List<NodeFinder> nodeFinders = new ArrayList<NodeFinder>();
    private List<NodeFilter> nodeFilters = new ArrayList<NodeFilter>();

    /**
     * 
     */
    public StandardNodeCrawlerImpl()
    {
        super();
    }

    /**
     * @param serviceRegistry
     */
    public StandardNodeCrawlerImpl(ServiceRegistry serviceRegistry)
    {
        super();
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * @param nodeService
     *            the nodeService to set
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
    }

    public Set<NodeRef> crawl(NodeRef... nodes)
    {
        return crawl(new HashSet<NodeRef>(Arrays.asList(nodes)));
    }

    public synchronized Set<NodeRef> crawl(Set<NodeRef> startingNodes)
    {
        init();
        Queue<NodeRef> nodesToProcess = new LinkedList<NodeRef>();
        nodesToProcess.addAll(startingNodes);
        Set<NodeRef> resultingNodeSet = new HashSet<NodeRef>(89);
        Set<NodeRef> processedNodes = new HashSet<NodeRef>(89);

        // Do we have any more nodes to process?
        while (nodesToProcess.peek() != null)
        {
            // Yes, we do. Read the next noderef from the queue.
            NodeRef thisNode = nodesToProcess.poll();
            // Check that we haven't already processed it. Skip it if we have, process it if we haven't
            if (!processedNodes.contains(thisNode))
            {
                // Record the fact that we're processing this node
                processedNodes.add(thisNode);
                // We check this node against any filters that are in place (the nodes
                // that we were given to start with are always processed)
                if (startingNodes.contains(thisNode) || includeNode(thisNode))
                {
                    resultingNodeSet.add(thisNode);
                    Set<NodeRef> subsequentNodes = findSubsequentNodes(thisNode);
                    for (NodeRef node : subsequentNodes)
                    {
                        nodesToProcess.add(node);
                    }
                }
            }
        }
        return resultingNodeSet;
    }

    /**
     * 
     */
    private void init()
    {
        for (NodeFinder nodeFinder : this.nodeFinders)
        {
            nodeFinder.setServiceRegistry(serviceRegistry);
            nodeFinder.init();
        }
        for (NodeFilter nodeFilter : this.nodeFilters)
        {
            nodeFilter.setServiceRegistry(serviceRegistry);
            nodeFilter.init();
        }
    }

    /**
     * @param thisNode
     * @return
     */
    private Set<NodeRef> findSubsequentNodes(NodeRef thisNode)
    {
        Set<NodeRef> foundNodes = new HashSet<NodeRef>(89);
        for (NodeFinder finder : nodeFinders)
        {
            foundNodes.addAll(finder.findFrom(thisNode));
        }
        return foundNodes;
    }

    /**
     * @param thisNode
     * @return
     */
    private boolean includeNode(NodeRef thisNode)
    {
        boolean include = true;
        for (int i = 0; include && (i < nodeFilters.size()); ++i)
        {
            include &= nodeFilters.get(i).accept(thisNode);
        }
        return include;
    }

    public synchronized void setNodeFinders(NodeFinder... finders)
    {
        nodeFinders = Arrays.asList(finders);
    }

    public synchronized void setNodeFilters(NodeFilter... filters)
    {
        nodeFilters = Arrays.asList(filters);
    }
}
