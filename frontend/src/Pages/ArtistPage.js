import {Button, Divider, Form, Input, List, Space, Tooltip} from "antd";
import React, {useEffect, useState} from 'react'
import axios from "axios";
import {Content} from "antd/es/layout/layout";
import {Link, useHistory} from "react-router-dom";
import {CloseOutlined, EditOutlined, PlusOutlined} from "@ant-design/icons";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";
import getCover from "../Elements/getCover";
import SongsList from "../Elements/SongsList";

function ArtistPage(props) {

    const [artistView, setArtistView] = useState({artist:{}, songs: [{song:{artist:{}, album:{}}, like:{}}], albums: []})

    const getArtist = () => {
        axios.get("http://localhost:8080/api/v1/artist/", {
            withCredentials:true,
            params: {id:props.match.params.id}
        }).then(res => {
            setArtistView(res.data)
            console.log(props.userView)
            console.log("artist", artistView.songs)
        })
    }

    useEffect(() => {
        getArtist();
    }, []);

    const onFinish = (values) => {
        axios.put("http://localhost:8080/api/v1/artist/", {
            "name":values.name,
            "id":props.match.params.id,
            "ownerID":props.userView.user.id
        }, {withCredentials:true}).then(r=>{
            getArtist()
        })
    };

    const [isModalVisible, setIsModalVisible] = useState(false);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleOk = () => {
        setIsModalVisible(false);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    let history = useHistory()

    const deleteArtist = () => {
        axios.delete("http://localhost:8080/api/v1/artist/", {
            params: {id:props.match.params.id}
        }, {withCredentials:true}).then(r => {
            console.log(r)
            history.push("/owner/")
        })
    }

    const newDefaultName = "Album №" + artistView.albums.length

    const getArtistEditButton = () => {
        if (artistView.artist.ownerID === props.userView.user.id)
            return <Button icon={<EditOutlined/>} type="primary" onClick={showModal}>
                Edit Artist
            </Button>
        return ;
    }

    const getAlbumCreateButton = () => {
        if (artistView.artist.ownerID === props.userView.user.id)
            return <Tooltip title="create Album">
                <Button onClick={createAlbum} type="primary" shape="square" icon={<PlusOutlined />} >
                    Add Album
                </Button>
            </Tooltip>
        return
    }

    const createAlbum = () => {
        axios.post("http://localhost:8080/api/v1/album/", {
            name:newDefaultName,
            artist:{
                id:props.match.params.id
            }
        }, {withCredentials:true}).then(
            r=>{
                getArtist()
            }
        )
    }

    function getModal() {
        return <Modal title="Edit playlist" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
            <Form
                name="basic"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
            >
                <Form.Item
                    label="Name"
                    name="name"
                    rules={[
                        {
                            message: 'Update Name',
                        },
                    ]}
                >
                    <Input placeholder={artistView.artist.name}/>
                </Form.Item>

                <Form.Item
                    name="remember"
                    valuePropName="checked"
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Space size={170}>
                        <Button icon={<CloseOutlined />} type="primary"onClick={deleteArtist}>
                            Delete
                        </Button>
                        <Button type="primary" htmlType="submit">
                            Save
                        </Button>
                    </Space>
                </Form.Item>
            </Form>
        </Modal>
    }

    return (<Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                <Space size={200}>
                    {getCover(artistView.artist.uuid, 400, 'playlistDefault.png')}
                    <List>
                        <List.Item>
                            <h1>{artistView.artist.name}</h1>
                        </List.Item>
                        <List.Item>
                            {getArtistEditButton()}
                        </List.Item>
                    </List>
                </Space>
                {getModal()}
                <Divider orientation="left">Albums:</Divider>
                <List
                    size="large"
                    bordered
                    dataSource={artistView.albums}
                    header={getAlbumCreateButton()}
                    renderItem={item =>
                        <List.Item>
                            <Space size={100}>
                                <Link to={`/album/${item.id}`}>
                                    {getCover(item.uuid, 200, 'playlistDefault.png')}
                                </Link>
                                <Link to={`/album/${item.id}`}>
                                    <h1>{item.name}</h1>
                                </Link>
                            </Space>
                        </List.Item>}
                >
                </List>
                {/*<SongsList*/}
                {/*    updatePage={getArtist}*/}
                {/*    isPlaying={props.isPlaying}*/}
                {/*    setIsPlaying={props.setIsPlaying}*/}
                {/*    setSongList={props.setSongList}*/}
                {/*    setIsActive={props.setIsActive}*/}
                {/*    setCurrentSongIndex={props.setCurrentSongIndex}*/}
                {/*    songList={props.songList}*/}
                {/*    songs={artistView.songs}*/}
                {/*    isPlaylist={false}*/}
                {/*    // playlists={props.userView.playlistLists}*/}
                {/*    currentSongIndex={props.currentSongIndex}*/}
                {/*    userView={props.userView}*/}
                {/*/>*/}
            </div>
        </Content>)
}

export default ArtistPage