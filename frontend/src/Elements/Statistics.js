import {Divider, List, Progress, Space} from "antd";
import React, {useEffect, useState} from 'react'
import axios from "axios";

function Statistics(props) {

    function getAveragePercent(index) {
        let percents = 0;
        let count = 0;
        if (props.stat[index] == undefined || props.stat[index].listenings == undefined
        || props.stat[index].listenings.length == 0)
            return 0

        props.stat[index].listenings.forEach(
            listening => {
                percents += listening.seconds / listening.song.duration;
                count++;
            }
        )

        return Math.trunc(percents / count * 100);
    }

    return (<div>
        <Divider orientation="left">Statistics:</Divider>
        <List
            size="large"
            bordered
            rowKey
            dataSource={props.stat}
            renderItem={(item, index) =>
                <List.Item>
                    <Space size={"large"}>
                        <h1>{item.region.name}:</h1>
                        <Space direction="vertical">
                            <Progress type="circle" percent={getAveragePercent(index)} width={200} />
                            <h1>Average Song Listening</h1>
                        </Space>
                        <Space direction="vertical">
                            <h1>{item.listenings.length}</h1>
                            <h1>Number of listening</h1>
                        </Space>
                        <Space direction="vertical">
                            <h1>{(item.listenings.length * item.region.rate).toFixed(2)} $</h1>
                            <h1>Received money</h1>
                        </Space>
                    </Space>
                </List.Item>}
        /></div>)
}

export default Statistics