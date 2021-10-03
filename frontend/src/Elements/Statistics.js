import {Divider, List, Progress, Space} from "antd";
import React, {useEffect, useState} from 'react'
import axios from "axios";

function Statistics(props) {

    const [summaryProfit, setSummaryProfit] = useState(0)
    const [summaryPlays, setSummaryPlays] = useState(0)

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

    useEffect(() => {
        let sum = 0
        let playSum = 0
        props.stat.forEach(
            regionlist => {
                sum += regionlist.listenings.length * regionlist.region.rate
                playSum += regionlist.listenings.length
            }
        )
        setSummaryProfit(sum)
        setSummaryPlays(playSum)
    }, [])

    function getProfitShare(index) {

        if (props.stat[index] == undefined || props.stat[index].listenings == undefined
            || props.stat[index].listenings.length == 0 || summaryProfit == 0)
            return 0

        return Math.trunc(props.stat[index].listenings.length * props.stat[index].region.rate / summaryProfit * 100)
    }

    function getPlaysShare(index) {
        if (props.stat[index] == undefined || props.stat[index].listenings == undefined
            || props.stat[index].listenings.length == 0 || summaryProfit == 0)
            return 0

        return Math.trunc(props.stat[index].listenings.length / summaryPlays * 100)
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
                    <Space direction={'vertical'}>
                        <Space>
                            <h1 style={{fontSize:"30px"}}>{item.region.name}</h1>
                        </Space>
                        <Space size={50}>
                            <Space>
                                <h1 style={{fontSize:"30px"}}>Profile:</h1>
                                <h1>{(item.listenings.length * item.region.rate).toFixed(2)} $</h1>
                            </Space>
                            <Space>
                                <h1 style={{fontSize:"30px"}}>Number of plays:</h1>
                                <h1>{item.listenings.length}</h1>
                            </Space>
                        </Space>
                        <Space size={100}>
                            <Space direction="vertical">
                                <Progress type="circle" percent={getAveragePercent(index)} width={200} />
                                <h1>Average Song Listening</h1>
                            </Space>
                            <Space direction="vertical">
                                <Progress type="circle" percent={getProfitShare(index)} width={200}/>
                                <h1>Profit share</h1>
                            </Space>
                            <Space direction="vertical">
                                <Progress type="circle" percent={getPlaysShare(index)} width={200} />
                                <h1>Plays share</h1>
                            </Space>
                        </Space>
                    </Space>
                </List.Item>}
        /></div>)
}

export default Statistics