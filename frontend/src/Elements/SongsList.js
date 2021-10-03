import {Button, Divider, Image, List, Space, Tooltip} from "antd";
import {CaretRightOutlined, CloseOutlined, EditOutlined, EllipsisOutlined, PauseOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";
import React, {useState} from "react";
import axios from "axios";
import SongInfoModal from "./SongInfoModal";
import Like from "./Like";
import SongEditModal from "./SongEditModal";
import GetImage from "./GetImage";

function SongsList (props) {

    const [editedSong, setEditedSong] = useState({})

    const [isInfoModalVisible, setIsInfoModalVisible] = useState(false);

    const showSongInfoModal = (item) => {
        setEditedSong(item)
        setIsInfoModalVisible(true);
    };

    const songInfoHandleOk = () => {
        setIsInfoModalVisible(false);
    };

    const songInfoHandleCancel = () => {
        setIsInfoModalVisible(false);
    };

    const [isEditModalVisible, setIsEditModalVisible] = useState(false);

    const showSongModal = (item) => {
        setEditedSong(item)
        setIsEditModalVisible(true);
    };

    const showEditInfoModal = (item) => {
        setEditedSong(item)
        setIsEditModalVisible(true);
    };

    const songEditHandleOk = () => {
        setIsEditModalVisible(false);
    };

    const songEditHandleCancel = () => {
        setIsEditModalVisible(false);
    };

    const deleteSong = (song) => {
        axios.delete("http://localhost:8080/api/v1/playlist/song/", {
            withCredentials:true,
            params:{
                addedSongId:song.id
            }
        }).then(
            r=>props.updatePage()
        )
    }

    const playSong = (index, isNewListening, isSameSong) => {
        if (props.isPlaying && isSameSong) {
            props.setIsPlaying(false)
            props.setForcePlay(!props.forcePlay)
        } else {
            console.log("HERE")
            props.setIsActive(true)
            props.setForcePlay(!props.forcePlay)
            props.setIsPlaying(true)
            props.setSongList(props.songs)
            props.setCurrentSongIndex(index)
            if (isNewListening) {
                props.setSeconds(-1)
            }
        }
    }

    function SongEditButton(props) {
        if (props.item.ownerID === props.user.id) {
            return <Button icon={<EditOutlined />} type="primary" shape={"circle"} onClick={() => showSongModal(props.item)}/>        }
        return <div/>
    }

    function RemoveButton(props) {
        if (props.isPlaylist === true)
            return <Tooltip title="Remove">
                <Button type="primary" size={"small"} shape="circle" icon={<CloseOutlined />} onClick={() => deleteSong(props.item)}/>
            </Tooltip>
        return <div/>
    }

    return (
        <div>
            <SongInfoModal
                isInfoModalVisible={isInfoModalVisible}
                songInfoHandleOk={songInfoHandleOk}
                songInfoHandleCancel={songInfoHandleCancel}
                playlists={props.playlists}
                editedSong={editedSong}
            />
            <SongEditModal
                isEditModalVisible={isEditModalVisible}
                songEditHandleOk={songEditHandleOk}
                songEditHandleCancel={songEditHandleCancel}
                playlists={props.playlists}
                editedSong={editedSong}
                setIsEditModalVisible={setIsEditModalVisible}
                updatePage={props.updatePage}
            />
            <Divider orientation="left">Songs:</Divider>
            <List
                size="small"
                bordered
                rowKey
                dataSource={props.songs}
                renderItem={(item, index) => {
                    let playIcon = <CaretRightOutlined />
                    let disabled = false
                    let isNewListening = true
                    let isSameSong = false
                    if (item.song.uuid === null) {
                        disabled = true
                    }
                    else if (item.song.id === props.songList[props.currentSongIndex].song.id) {
                        isSameSong = true
                        isNewListening = false
                        if (!props.isPlaying) {
                            playIcon = <CaretRightOutlined />
                        } else {
                            playIcon = <PauseOutlined />
                        }
                    }
                    return <List.Item>
                        <Space>
                            {index + 1}
                            <Tooltip title="Play">
                                <Button disabled={disabled} type="primary" shape={"circle"} icon={playIcon}
                                        onClick={() => playSong(index, isNewListening, isSameSong)}>
                                </Button>
                            </Tooltip>
                            <GetImage
                                uuid={item.song.album.uuid}
                                w={80}
                                defaultName={"playlistDefault.png"}
                            />
                            <Space direction={"vertical"} size={0} split={<Divider style={{marginBlock:"0px"}} type="horizontal" />}>
                                <h1 style={{fontSize:"16px", marginBlock:"0px", textAlign:"center"}}>{item.song.title}</h1>
                                <Space>
                                    <Link to={`/artist/${item.song.artist.id}`}>
                                        <p style={{fontSize:"14px"}}>{item.song.artist.name}</p>
                                    </Link>
                                    <Link to={`/album/${item.song.album.id}`}>
                                        <p style={{fontSize:"15px"}}>{item.song.album.name}</p>
                                    </Link>
                                </Space>
                            </Space>
                            <p>{Math.trunc(item.song.duration / 60)} {": "}
                                { ('00'+Math.trunc(item.song.duration % 60)).slice(-2)}</p>
                            <Like
                                item={item}
                                updatePage={props.updatePage}/>
                            <Button icon={<EllipsisOutlined />} type="primary" shape={"circle"} onClick={() => showSongInfoModal(item.song)}/>
                            <SongEditButton
                                item={item.song}
                                user={props.userView.user}/>
                            <RemoveButton
                                isPlaylist={props.isPlaylist}
                                item={item}/>
                        </Space>
                    </List.Item>
                }
                }
            />
        </div>
    )
}

export default SongsList