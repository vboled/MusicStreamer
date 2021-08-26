import {useEffect, useState} from "react";
import axios from "axios";
import {Content, Header} from "antd/es/layout/layout";
import {Button, Divider, Form, Image, Input, Layout, List, Space, Tooltip} from "antd";
import {Link, useHistory} from "react-router-dom";
import {CaretRightOutlined, CloseOutlined, EditOutlined, PlusCircleOutlined} from "@ant-design/icons";
import SongList from "../Elements/SongList";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";
import MyHeader from "../Elements/Header";
import getCover from "../Elements/getCover";

function AlbumPage({match}) {

    const [isModalVisible, setIsModalVisible] = useState(false);
    const [isEditModalVisible, setIsEditModalVisible] = useState(false);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const showSongModal = () => {
        setIsEditModalVisible(true);
    };

    const songHandleOk = () => {
        setIsEditModalVisible(false);
    };

    const songHandleCancel = () => {
        setIsEditModalVisible(false);
    };

    const songOnFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const songOnFinish = (values) => {
        axios.put("http://localhost:8080/api/v1/songs/song/", {
            "title":values.name,
            "id":match.params.id
        }).then(r=>{
                getAlbum()
            }
        )
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

    const [albumView, setAlbumView] = useState({album:{}, songs: []})

    const getAlbum = () => {
        axios.get("http://localhost:8080/api/v1/album/", {
            params: {id:match.params.id}
        }).then(res => {
            console.log(res.data)
            setAlbumView(res.data)
        })
    }

    const onFinish = (values) => {
        axios.put("http://localhost:8080/api/v1/album/", {
            "name":values.name,
            "id":match.params.id
        }).then(r=>{
                getAlbum()
        }
        )
    };

    const [viewer, setViewer] = useState({})
    const [editedSong, setEditedSong] = useState({})
    let history = useHistory()

    const amIOwner = () => {
        axios.get("http://localhost:8080/api/v1/whoami").then(res => {
            setViewer(res.data)
        });
    }

    useEffect(() => {
        getAlbum();
        amIOwner();
    }, []);

    const deleteAlbum = () => {
        axios.delete("http://localhost:8080/api/v1/album/", {
            params: {id:match.params.id}
        }).then(r => {
            history.push("/owner/")
            console.log(r)
        })
    }

    const newDefaultName = "Song №" + albumView.songs.length

    const deleteSong = () => {
        axios.delete("http://localhost:8080/api/v1/songs/", {
            params:{
                id:editedSong.id
            }
        }).then(r => {
            setIsEditModalVisible(false);
            getAlbum()
        })
    }

    const createSong = () => {
        axios.post("http://localhost:8080/api/v1/songs/create/", {
            "title":newDefaultName,
            "ownerID":viewer.id,
            "album":{
                "id": match.params.id
            },
            "artist": albumView.album.artist
        }).then(r=>{
            getAlbum()
        })
    }

    function getAlbumEditButton() {
        if (albumView.album.ownerID === viewer.id)
            return <Space size={"large"}>
                        <Button icon={<EditOutlined/>} type="primary" onClick={showModal}>
                            Edit Album
                        </Button>
                        <Button icon={<PlusCircleOutlined />} type="primary" onClick={createSong}>
                            Add Song
                        </Button>
                </Space>
        return ;
    }

    function getSongEditButton() {
        if (albumView.album.ownerID === viewer.id)
            return <Button icon={<EditOutlined />} type="primary" shape={"circle"} onClick={showSongModal}/>
        return ;
    }

    function getEditModal() {

        return <Modal title="Edit album" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
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
                    <Input placeholder={albumView.album.name}/>
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
                        <Button icon={<CloseOutlined />} type="primary"onClick={deleteAlbum}>
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

    function getSongEditModal() {

        return <Modal title="Edit Song" visible={isEditModalVisible} onOk={songHandleOk} onCancel={songHandleCancel}>
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
                onFinish={songOnFinish}
                onFinishFailed={songOnFinishFailed}
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
                    <Input placeholder={editedSong.title}/>
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
                        <Button icon={<CloseOutlined />} type="primary"onClick={deleteSong}>
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

    return <Layout>
        {MyHeader()}
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                <Space size={200}>
                    {getCover(albumView.album.uuid, 400, 'playlistDefault.png')}
                    <List>
                        <List.Item>
                            <h1>{albumView.album.name}</h1>
                        </List.Item>
                        <List.Item>
                            <h1>{albumView.songs.length} tracks</h1>
                        </List.Item>
                        <List.Item>
                            <h1>Length</h1>
                        </List.Item>
                        <List.Item>
                            <p>{albumView.album.releaseDate}</p>
                        </List.Item>
                        <List.Item>
                            {getAlbumEditButton()}
                        </List.Item>
                    </List>
                </Space>
                {getEditModal()}
            </div>
            {getSongEditModal()}
            <Divider orientation="left">Songs:</Divider>
            <List
                size="small"
                bordered
                rowKey
                dataSource={albumView.songs}
                renderItem={(item, index) =>
                    <List.Item>
                        <Space>
                            {index + 1}
                            <Tooltip title="Play">
                                <Button type="primary" shape="circle" icon={<CaretRightOutlined />} />
                            </Tooltip>
                            {item.title}
                            <Link to={`/artist/${item.artist.id}`}>
                                {item.artist.name}
                            </Link>
                            <Link to={`/album/${item.album.id}`}>
                                {item.album.name}
                            </Link>
                            {setEditedSong(item)}
                            {getSongEditButton()}
                        </Space>
                    </List.Item>}
            />
        </Content>
    </Layout>
}

export default AlbumPage