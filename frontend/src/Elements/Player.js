import React, {useCallback, useEffect, useRef, useState} from "react";
import {List, Space} from "antd";
import GetImage from "./GetImage";
import {Link} from "react-router-dom";
import PlayerControls from "./PlayerControls";
import axios from "axios";

function Player(props) {

    const audioEl = useRef(null);

    const RunSeconds = () => {
        props.setSeconds(seconds => seconds + 1)
    }

    useEffect(() => {
        if (props.isActive) {
            if (props.isPlaying) {
                audioEl.current.play();
                const flag = setInterval(() => RunSeconds(), 1000);
                return () => clearInterval(flag);
            } else {
                audioEl.current.pause();
            }
        }
    }, [props.isPlaying, props.isActive])

    useEffect(() => {
        if (props.isActive && props.isPlaying) {
            if (props.seconds % 3 === 0) {
                if (props.seconds === 0) {
                    console.log("CREATE")
                    axios.post("http://localhost:8080/api/v1/listening/",
                        {
                            seconds:0,
                            user:{id:props.userView.user.id},
                            song:{id:props.songList[props.currentSongIndex].song.id}
                        }, {withCredentials:true}).then(r => {
                            if (r.status === 200) {
                                props.setListeningID(r.data.id)
                            }
                    })
                } else {
                    console.log("UPDATE")
                    axios.put("http://localhost:8080/api/v1/listening/",
                        {
                        }, {withCredentials:true,
                            params:{
                                id:props.listeningID,
                                seconds:props.seconds
                            }}).then(r => {
                        if (r.status === 201) {
                            props.setListeningID(r.data.id)
                            console.log(r.data.id)
                        }
                    })
                }
            }

        }
    }, [props.seconds])

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
                        setSeconds={props.setSeconds}
                        isPlaying={props.isPlaying}
                        setIsPlaying={props.setIsPlaying}
                        uuid={props.songList[props.currentSongIndex].song.uuid}/>
                    <h1 style={{color:"#ffffff"}}>{Math.trunc((props.seconds + 1) / 60)} {": "}
                        { ('00'+Math.trunc((props.seconds + 1) % 60)).slice(-2)} {"/ "} {Math.trunc(props.songList[props.currentSongIndex].song.duration / 60)} {": "}
                    { ('00'+Math.trunc(props.songList[props.currentSongIndex].song.duration % 60)).slice(-2)}</h1>
                </Space>
            </div>
        )
    return <div/>
}

export default Player