import {Divider, List, Space} from "antd";
import React, {useEffect, useState} from 'react'
import axios from "axios";

function Statistics(props) {

    return (<div>
        <Divider orientation="left">Statistics:</Divider>
        <List
            size="large"
            bordered
            rowKey
            dataSource={props.stat}
            renderItem={(item) =>
                <List.Item>
                    <Space
                        direction="vertical"
                    >
                        <h1>{item.region.name}:</h1>
                        <h1>Number of listenings: {item.listenings.length}</h1>
                        <h1>received money: {(item.listenings.length * item.region.rate).toFixed(2)} $</h1>
                    </Space>
                </List.Item>}
        /></div>)
}

export default Statistics