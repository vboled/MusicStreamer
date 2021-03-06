import {Button, Divider, Image, List, Space, Tooltip} from "antd";
import {Content} from "antd/es/layout/layout";
import React, {useEffect, useState} from 'react'
import "../App.css"
import 'antd/dist/antd.css';
import axios from "axios";
import {Link} from "react-router-dom";
import {PlusOutlined} from "@ant-design/icons";
import AlbumList from "../Elements/AlbumList";
import SongsList from "../Elements/SongsList";

function getCover(uuid, w) {
    let name = uuid
    if (name === null)
        name = 'playlistDefault.png'
    return <Image
        width={w}
        preview={false}
        src={window.location.origin + '/img/' + name}
    />
}

function ContentPage() {

    const [contentView, setContentView] = useState({owner:{}, artists:[], albums: []})

    const getContent = () => {
        axios.get("http://localhost:8080/api/v1/user/content/", {withCredentials:true}).then(res => {
            console.log(res.data)
            setContentView(res.data)
        })
    }

    useEffect(() => {
        getContent();
    }, []);

    const newDefaultName = "Artist №" + contentView.artists.length

    const createArtist = () => {
        console.log("cv", contentView)
        axios.post("http://localhost:8080/api/v1/artist/", {
            ownerID:contentView.owner.id,
            name:newDefaultName
        }, {withCredentials:true}).then(r => {
            getContent();
        })

    }

    return (<Content style={{ margin: '24px 16px 0' }}>
        <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
            <Divider orientation="left">
                Artists:
            </Divider>
            <List
                size="large"
                bordered
                header={
                    <Tooltip title="create Artist">
                        <Button onClick={createArtist} type="primary" shape="square" icon={<PlusOutlined />} >
                            Add Artist
                        </Button>
                    </Tooltip>}
                dataSource={contentView.artists}
                renderItem={item =>
                    <List.Item>
                        <Space size={100}>
                            <Link to={`/artist/${item.id}`}>
                                {getCover(item.uuid, 200)}
                            </Link>
                            <Link to={`/artist/${item.id}`}>
                                <h1>{item.name}</h1>
                            </Link>
                        </Space>
                    </List.Item>}
            >
            </List>
            {AlbumList(contentView.albums)}
        </div>
    </Content>)
}

export default ContentPage