const Leaderboard = ({playerList}) => {

    return (
        <div className="leaderboard-output">
            {playerList && playerList.map((player, index) => (
                <p key={index}> {player.playerName} {player.playerSuccessRate} </p>
            ))}
        </div>
    );

}

export default Leaderboard;