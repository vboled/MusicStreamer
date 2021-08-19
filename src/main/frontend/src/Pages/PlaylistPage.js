import {useEffect, useState} from "react";
import axios from "axios";
import {Content, Header} from "antd/es/layout/layout";
import {Button, Divider, Form, Image, Input, Layout, List, Popover, Space, Tooltip} from "antd";
import {withRouter, Link} from "react-router-dom";
import {CaretRightOutlined, CloseOutlined, EditOutlined} from "@ant-design/icons";
import SongList from "../Elements/SongList";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";

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


function PlaylistPage({match}) {
    let playlistId = match.params.id;

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

    const [playlistDto, setPlaylistDto] = useState({playlist:{}, songs: []})

    const getPlaylist = () => {
        axios.get("http://localhost:8080/api/v1/playlist/", {
            params: {id:playlistId}
        }).then(res => {
            console.log(res.data)
            setPlaylistDto(res.data)
        })
    }

    useEffect(() => {
        getPlaylist();
    }, []);

    const onFinish = (values) => {
        axios.put("http://localhost:8080/api/v1/playlist/", {
            "name":values.name,
            "description":values.description,
            "id":1
        })
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    let onlySongs = []

    playlistDto.songs.map(song => {
        onlySongs.push(song.song)
    })

    const deletePlaylist = () => {
        axios.delete("http://localhost:8080/api/v1/playlist/", {
            params: {playlistID:playlistId}
        }).then(r => {
            // if (r.status)
            console.log(r)
        })
    }

    return <Layout>
        <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                {getCover(playlistDto.playlist.uuid, 400)}
                <h1>{playlistDto.playlist.name}</h1>
                <h1>{playlistDto.songs.length} tracks</h1>
                <h1>Length</h1>
                <p>{playlistDto.playlist.description}</p>
                <>
                    <Button icon={<EditOutlined/>} type="primary" onClick={showModal}>
                        Edit Playlist
                    </Button>
                    <Modal title="Edit playlist" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
                        {getCover(playlistDto.playlist.uuid, 200)}
                        <Button icon={<CloseOutlined />} type="primary"onClick={deletePlaylist}>
                            Delete
                        </Button>
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
                                <Input placeholder={playlistDto.playlist.name}/>
                            </Form.Item>

                            <Form.Item
                                label="Description"
                                name="description"
                                rules={[
                                    {
                                        message: 'Update Description',
                                    },
                                ]}
                            >
                                <Input placeholder={playlistDto.playlist.description}/>
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
                                <Button type="primary" htmlType="submit">
                                    Save
                                </Button>
                            </Form.Item>
                        </Form>
                    </Modal>
                </>
                {SongList(onlySongs)}
            </div>
        </Content>
    </Layout>
}

export default PlaylistPage