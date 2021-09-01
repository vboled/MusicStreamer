import {useEffect, useState} from "react";
import React from 'react'
import axios from "axios";
import {Content} from "antd/es/layout/layout";
import {
    Button,
    DatePicker,
    Divider,
    Form,
    Input,
    List,
    Menu,
    Select,
    Space,
    Tooltip
} from "antd";
import {Link, useHistory} from "react-router-dom";
import {
    CaretRightOutlined,
    CloseOutlined,
    EditOutlined, EllipsisOutlined, HeartFilled, HeartOutlined,
    PlusCircleOutlined
} from "@ant-design/icons";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";
import getCover from "../Elements/getCover";
import {Option} from "antd/es/mentions";
import SongsList from "../Elements/SongsList";

const { SubMenu } = Menu;

function AlbumPage(props) {

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

    const [albumView, setAlbumView] = useState({album:{}, songs: [{song:{artist:{}, album:{}}, like:{}}]})

    const getAlbum = () => {
        axios.get("http://localhost:8080/api/v1/album/", {
            params: {id:props.match.params.id}
        }).then(res => {
            setAlbumView(res.data)
            console.log("songs", albumView.songs)
        })
    }

    const onFinish = (values) => {
        let data = {
            "name":values.name,
            "volume":values.volume,
            "id":props.match.params.id,
        }
        if (albumType[0] !== "none")
            data.type = albumType[0];
        if (relAlbumDate[0] !== "none")
            data.releaseDate = relAlbumDate[0]
        axios.put("http://localhost:8080/api/v1/album/", data)
            .then(r=>{
                getAlbum()
        }
        )
    };

    let relAlbumDate = useState("none")
    let albumType = useState("none")

    let history = useHistory()

    useEffect(() => {
        getAlbum();
    }, []);

    const deleteAlbum = () => {
        axios.delete("http://localhost:8080/api/v1/album/", {
            params: {id:props.match.params.id}
        }).then(r => {
            history.push("/owner/")
            console.log(r)
        })
    }

    const newDefaultName = "Song №" + albumView.songs.length

    const createSong = () => {
        axios.post("http://localhost:8080/api/v1/songs/create/", {
            "title":newDefaultName,
            "ownerID":props.userView.user.id,
            "album":{
                "id": props.match.params.id
            },
            "artist": albumView.album.artist
        }).then(r=>{
            getAlbum()
        })
    }

    function getAlbumEditButton() {
        if (albumView.album.ownerID === props.userView.user.id)
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

    function albumReleaseChange(date, dateString) {
        relAlbumDate[0] = dateString
    }

    function selectTypeHandler(value) {
        albumType[0] = value
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
                    label="Volumes"
                    name="volume"
                    rules={[
                        {
                            message: 'Update number of disks',
                        },
                    ]}
                >
                    <Input placeholder={albumView.album.volume}/>
                </Form.Item>

                <Form.Item
                    label="Release"
                >
                    <DatePicker onChange={albumReleaseChange} />
                </Form.Item>

                <Form.Item
                    label="Release"
                >
                    <Select defaultValue="Album" style={{ width: 120 }} onChange={selectTypeHandler}>
                        <Option value="album">Album</Option>
                        <Option value="ep">EP</Option>
                        <Option value="single">Single</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Type"
                    name="type"
                    rules={[
                        {
                            message: 'Update type',
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
                        <Button icon={<CloseOutlined />} type="primary" onClick={deleteAlbum}>
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
        <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
            <Space size={200}>
                {getCover(albumView.album.uuid, 400, 'playlistDefault.png')}
                <List>
                    <List.Item>
                        <p>{albumView.album.type}</p>
                    </List.Item>
                    <List.Item>
                        <h1 style={{fontSize:"40px"}}>{albumView.album.name}</h1>
                    </List.Item>
                    <List.Item>
                        <h1>{albumView.songs.length} tracks</h1>
                    </List.Item>
                    <List.Item>
                        <h1>Length</h1>
                    </List.Item>
                    {/*<List.Item>*/}
                    {/*    <p>{albumView.album.releaseDate.split("-")[0]}</p>*/}
                    {/*</List.Item>*/}
                    <List.Item>
                        <p>"Artists"</p>
                    </List.Item>
                    <List.Item>
                        {getAlbumEditButton()}
                    </List.Item>
                </List>
            </Space>
            {getEditModal()}
            <SongsList
                updatePage={getAlbum}
                isPlaying={props.isPlaying}
                setIsPlaying={props.setIsPlaying}
                setSongList={props.setSongList}
                setIsActive={props.setIsActive}
                setCurrentSongIndex={props.setCurrentSongIndex}
                songList={props.songList}
                songs={albumView.songs}
                isPlaylist={false}
                playlists={props.userView.playlistLists}
                currentSongIndex={props.currentSongIndex}
                userView={props.userView}
            />
        </div>
    </Content>)
}

export default AlbumPage