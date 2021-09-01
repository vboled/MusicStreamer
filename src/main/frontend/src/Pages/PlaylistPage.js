import React, {useEffect, useState} from "react";
import axios from "axios";
import {Content} from "antd/es/layout/layout";
import {Button, Divider, Form, Image, Input, Layout, List, Popover, Space, Tooltip} from "antd";
import {Link, useHistory} from "react-router-dom";
import {CaretRightOutlined, CloseOutlined, EditOutlined, PauseOutlined} from "@ant-design/icons";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";
import SongsList from "../Elements/SongsList";

function PlaylistPage(props) {

    const [isModalVisible, setIsModalVisible] = useState(false);
    let history = useHistory()

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleOk = () => {
        setIsModalVisible(false);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const [playlistDto, setPlaylistDto] = useState({playlist:{}, songs: [{song:{album:{uuid:{}}, artist:{}}, playlist:{}}]})

    useEffect(() => {
        getPlaylist();
    }, []);


    const fileUploadHandler = () => {
        const fd = new FormData()
        fd.append('file', state, state.name)
        axios.put(`http://localhost:8080/api/v1/playlist/cover/${props.match.params.id}`,
            fd
        ).then(
            res => {
                getPlaylist()
            }
        )
    }

    const onFinish = (values) => {
        if (state !== undefined)
            fileUploadHandler()

        axios.put("http://localhost:8080/api/v1/playlist/", {
            "name":values.name,
            "description":values.description,
            "id":props.match.params.id
        }).then(r=>{
            getPlaylist()
        })
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const deletePlaylist = () => {
        axios.delete("http://localhost:8080/api/v1/playlist/", {
            params: {playlistID:props.match.params.id}
        }).then(r => {
            props.whoAmI()
            history.push("/playlist/")
            console.log(r)
        })
    }

    function getCover(uuid, w) {
        let name = uuid
        if (name === null)
            name = 'playlistDefault.png'
        if (playlistDto.playlist.main)
            name = 'favouritePlaylist.png'
        return <Image
            width={w}
            preview={false}
            src={window.location.origin + '/img/' + name}
        />
    }

    function getEditButton() {
        if (!playlistDto.playlist.main)
            return <List.Item>
                        <Button icon={<EditOutlined/>} type="primary" onClick={showModal}>
                            Edit Playlist
                        </Button>
                    </List.Item>
        return ;
    }

    const [state, setState] = useState()

    const fileSelectedHandler = (event) => {
        setState(event.target.files[0])
    }

    const getModal = () => {
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
                    label="Update Cover"
                    name="update"
                    rules={[
                        {
                            message: 'Update Cover',
                        },
                    ]}
                >
                    <Input type={"file"} onChange={fileSelectedHandler}/>
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Space size={170}>
                        <Button icon={<CloseOutlined />} type="primary"onClick={deletePlaylist}>
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

    const getPlaylist = () => {
        axios.get("http://localhost:8080/api/v1/playlist/", {
            params: {id:props.match.params.id}
        }).then(res => {
            console.log(res.data)
            setPlaylistDto(res.data)
        })

    }

    return (<Content style={{ margin: '24px 16px 0' }}>
        <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
            <Space size={200}>
                {getCover(playlistDto.playlist.uuid, 400)}
                <List>
                    <List.Item>
                        <h1>{playlistDto.playlist.name}</h1>
                    </List.Item>
                    <List.Item>
                        <h1>{playlistDto.songs.length} tracks</h1>
                    </List.Item>
                    <List.Item>
                        <h1>Length</h1>
                    </List.Item>
                    <List.Item>
                        <p>{playlistDto.playlist.description}</p>
                    </List.Item>
                    {getEditButton()}
                </List>
            </Space>
            {getModal()}
            <SongsList
                updatePage={getPlaylist}
                isPlaying={props.isPlaying}
                setIsPlaying={props.setIsPlaying}
                setSongList={props.setSongList}
                setIsActive={props.setIsActive}
                setCurrentSongIndex={props.setCurrentSongIndex}
                songList={props.songList}
                songs={playlistDto.songs}
                isPlaylist={true}
                playlists={props.userView.playlistLists}
                currentSongIndex={props.currentSongIndex}
                userView={props.userView}
            />
        </div>
    </Content>
    )
}

export default PlaylistPage