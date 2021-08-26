import {Button, Divider, Form, Image, Input, Layout, List, Space, Tooltip} from "antd";
import {Content, Header} from "antd/es/layout/layout";
import "../App.css"
import 'antd/dist/antd.css';
import axios from "axios";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {CloseOutlined, EditOutlined, PlusOutlined} from "@ant-design/icons";
import Modal from "antd/es/modal/Modal";
import MyHeader from "../Elements/Header";
import AlbumList from "../Elements/AlbumList";

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

    const getArtist = () => {
        axios.get("http://localhost:8080/api/v1/user/content/").then(res => {
            console.log(res.data)
            setContentView(res.data)
        })
    }

    useEffect(() => {
        getArtist();
    }, []);

    const newDefaultName = "Artist №" + contentView.artists.length

    const createArtist = () => {
        console.log("cv", contentView)
        axios.post("http://localhost:8080/api/v1/artist/", {
            ownerID:contentView.owner.id,
            name:newDefaultName
        }).then(r => {
            getArtist()
        })

    }

    return <Layout>
        {MyHeader()}
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
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
        </Content>
    </Layout>
}

export default ContentPage