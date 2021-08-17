import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {Button} from "antd";
import {BrowserRouter as Router, Link, Route, Switch, useHistory} from "react-router-dom";

const playlistStyle = {
    float:"left",
    marginLeft:"10px"
}

const DrawPlaylist = (playlist, index) => {

    return (
        <div style={playlistStyle} key={index}>
            <img src={window.location.origin + '/img/playlistDefault.png'} />
            <br/>
            <Link style={{textAlign: "center"}} to={`/playlist/${playlist.id}`}>{playlist.name}</Link>
        </div>)
}

export default DrawPlaylist;