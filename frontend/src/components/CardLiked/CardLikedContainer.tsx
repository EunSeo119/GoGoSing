import CardLiked from "./CardLiked";
import React, { useState, useRef } from "react";
import styles from "./CardLikedContainer.module.css";

interface AlbumProps {
  musicId:number;
  title:string;
  singer:string|null;
  songImg:string|null;
}
interface Props {
  albums: AlbumProps[];
}

const CardLikedContainer: React.FC<Props> = ({ albums }) => {
  const [startY, setStartY] = useState(0);
  const [scrollTop, setscrollTop] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  const containerRef = useRef<HTMLDivElement | null>(null);
  const [startX, setStartX] = useState(0);

  const handleStart = (
    e: React.TouchEvent<HTMLDivElement> | React.MouseEvent<HTMLDivElement>
  ) => {
    const y = "touches" in e ? e.touches[0].pageY : e.pageY;
    const x = "touches" in e ? e.touches[0].pageX : e.pageX; // X 축 시작 지점 추가

    if (containerRef.current) {
      setStartY(y);
      setStartX(x); // 저장
      setscrollTop(containerRef.current.scrollTop);
      setIsDragging(true);
    }
  };

  const handleMove = (
    e: React.TouchEvent<HTMLDivElement> | React.MouseEvent<HTMLDivElement>
  ) => {
    if (!isDragging || !containerRef.current) return;

    const currentY = "touches" in e ? e.touches[0].pageY : e.pageY;
    const currentX = "touches" in e ? e.touches[0].pageX : e.pageX;

    // 시작점과 현재 점 사이의 X, Y 차이 계산
    const diffX = Math.abs(currentX - startX);
    const diffY = Math.abs(currentY - startY);

    if (diffX > diffY) {
      return; // X축의 움직임이 더 클 경우 이벤트를 중지
    }

    e.preventDefault();

    const walk = currentY - startY;
    containerRef.current.scrollTop = scrollTop - walk;
  };

  const handleEnd = () => {
    setIsDragging(false);
  };

  /* onTouch 관련은 Mobile 환경에서 터치가 있을 때, onMouse는 Web 환경에서 Mobile 처럼 클릭하고 이동 할 때의 케이스 */
  return (
    <div
      className={styles.container}
      ref={containerRef}
      onTouchStart={handleStart}
      onTouchMove={handleMove}
      onTouchEnd={handleEnd}
      onMouseDown={handleStart}
      onMouseMove={handleMove}
      onMouseUp={handleEnd}
      onMouseLeave={handleEnd}
    >
      {albums.map((album) => {
        return <CardLiked album={album} />; // 각 ChartLong 컴포넌트에 album 데이터를 prop으로 전달합니다.
      })}
    </div>
  );
};

export default CardLikedContainer;
