package com.raoulvdberge.refinedstorage.api.network;

/**
 * Represents a network master, usually is a controller.
 */
public interface INetworkMaster {
    /*
	  Most contents removed, since RS conduits don't need any of the methods
	  and including them leads to more dependencies.
	 */

    /**
     * @return a graph of connected nodes to this network
     */
    INetworkNodeGraph getNodeGraph();
}
