package com.yeahmobi.loadbalance_manager.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.QueryResults;
import com.google.code.morphia.query.UpdateOperations;
import com.yeahmobi.loadbalance_manager.dao.NodeStatusDao;
import com.yeahmobi.loadbalance_manager.model.NodeStatus;
import com.yeahmobi.loadbalance_manager.model.NodeStatus.State;
import com.yeahmobi.loadbalance_manager.service.NodeStatusService;
import com.yeahmobi.loadbalance_manager.service.RegionService;

@Service
public class NodeStatusServiceImpl implements NodeStatusService {

    @Autowired
    private NodeStatusDao dao;

    @Autowired
    private RegionService regionService;

    public void updateAll(State state) {
        Query<NodeStatus> q = this.dao.getDatastore().createQuery(NodeStatus.class);

        UpdateOperations<NodeStatus> ops = this.dao.createUpdateOperations().set("state", state);
        this.dao.update(q, ops);
    }

    public void update(String region, State state) {
        Validate.isTrue(this.regionService.exists(region), "nodeStatus.region.notExist");

        Query<NodeStatus> q = this.dao.getDatastore().createQuery(NodeStatus.class);
        q.field("region").equal(region);

        UpdateOperations<NodeStatus> ops = this.dao.createUpdateOperations().set("state", state);
        this.dao.update(q, ops);
    }

    public void updateHeartbeat(String region, String nodeId, Date lastHeartbeatTime) {
        Validate.isTrue(this.regionService.exists(region), "nodeStatus.region.notExist");

        Query<NodeStatus> q = this.dao.getDatastore().createQuery(NodeStatus.class);
        q.field("region").equal(region);
        q.field("nodeId").equal(nodeId);

        UpdateOperations<NodeStatus> ops = this.dao.createUpdateOperations().set("lastHeartbeatTime", lastHeartbeatTime);
        this.dao.update(q, ops);
    }

    public void save(String region, String nodeId, State state, String detail) {
        Validate.isTrue(this.regionService.exists(region), "nodeStatus.region.notExist");

        Query<NodeStatus> q = this.dao.getDatastore().createQuery(NodeStatus.class);
        q.field("region").equal(region);
        q.field("nodeId").equal(nodeId);

        NodeStatus nodeStatus = this.dao.findOne(q);

        if (nodeStatus == null) {
            nodeStatus = new NodeStatus();
            nodeStatus.setRegion(region);
            nodeStatus.setNodeId(nodeId);
        }

        if (detail != null) {
            nodeStatus.setDetail(detail);
        }
        nodeStatus.setState(state);
        nodeStatus.setLastStatusUpdateTime(new Date());

        this.dao.save(nodeStatus);
    }

    public NodeStatus get(String region, String nodeId) {
        Validate.isTrue(this.regionService.exists(region), "nodeStatus.region.notExist");

        Query<NodeStatus> q = this.dao.getDatastore().createQuery(NodeStatus.class);
        q.field("region").equal(region);
        q.field("nodeId").equal(nodeId);

        return this.dao.findOne(q);
    }

    public List<NodeStatus> listAll() {
        QueryResults<NodeStatus> result = this.dao.find();
        List<NodeStatus> list = result.asList();

        return list;
    }

}