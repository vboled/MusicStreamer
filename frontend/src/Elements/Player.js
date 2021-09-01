import React, {useEffect, useRef, useState} from "react";
import {Button, List, Space, Tooltip} from "antd";
import {CaretRightOutlined, CloseOutlined, StepBackwardOutlined, StepForwardOutlined} from "@ant-design/icons";
import GetImage from "./GetImage";
import {Link} from "react-router-dom";
import PlayerControls from "./PlayerControls";

function Player(props) {

    const audioEl = useRef(null);

    useEffect(() => {
        if (props.isActive) {
            if (props.isPlaying) {
                audioEl.current.play();
            } else {
                audioEl.current.pause();
            }
        }
    })

    const SkipSong = (forwards = true) => {
        if (forwards) {
            props.setCurrentSongIndex(() => {
                let temp = props.currentSongIndex;
                temp++;

                if (temp > props.songList.length - 1) {
                    temp = 0;
                }
                return temp;
            })
        } else {
            props.setCurrentSongIndex(() => {
                let temp = props.currentSongIndex;
                temp--;

                if (temp < 0) {
                    temp = props.songList.length - 1;
                }
                return temp;
            })
        }
    }

    if (props.isActive)
        return (
            <div style={{position:"fixed", bottom:0, backgroundColor:"#001529", width:"100%", height:"170"}}>
                <audio src={'../audio/' + props.songList[props.currentSongIndex].song.uuid} ref={audioEl}></audio>
                <Space size={30}>
                    <div style={{marginTop:"10px"}}>
                        <GetImage
                            uuid={props.songList[props.currentSongIndex].song.album.uuid}
                            w={150}
                            defaultName={"playlistDefault.png"}
                        />
                    </div>
                    <div style={{marginTop:"10px"}}>
                        <List>
                            <List.Item>
                                <p style={{color:"#ffffff", fontSize:"20px", textAlign:"right"}}>
                                    {props.songList[props.currentSongIndex].song.title}
                                </p>
                            </List.Item>
                            <List.Item>
                                <Space>
                                    <Link to={`/album/${props.songList[props.currentSongIndex].song.album.id}`}>
                                        <p style={{color:"#ffffff"}}>
                                            {props.songList[props.currentSongIndex].song.album.name}
                                        </p>
                                    </Link>
                                    <Link to={`/artist/${props.songList[props.currentSongIndex].song.artist.id}`}>
                                        <p style={{color:"#ffffff"}}>
                                            {props.songList[props.currentSongIndex].song.artist.name}
                                        </p>
                                    </Link>
                                </Space>
                            </List.Item>
                        </List>
                    </div>
                    <PlayerControls
                        SkipSong={SkipSong}
                        isPlaying={props.isPlaying}
                        setIsPlaying={props.setIsPlaying}
                        uuid={props.songList[props.currentSongIndex].song.uuid}/>
                </Space>
            </div>
        )
    return <div/>
}

export default Player