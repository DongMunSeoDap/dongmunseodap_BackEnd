**🪴 Branch Convention (GitHub Flow)**

- `main`: 배포 가능한 브랜치, 항상 배포 가능한 상태를 유지
- `feature/{description}`: 새로운 기능을 개발하는 브랜치
    - 예: `feature/social-login`
- 브랜치 공유 X

**Flow**

1. `develop` 브랜치에서 새로운 브랜치를 생성. → `develop` 브랜치는 `main` 브랜치에서 생성
2. 작업을 완료하고 커밋 메시지에 맞게 커밋 후 푸시.
3. `develop` 으로 병합 시 Pull Request를 생성 / 팀원들의 리뷰.
4. 리뷰가 완료되면 `develop` 브랜치로 병합.
5. 병합 후, 배포 필요 시 `main` 브랜치로 Pull Request를 생성 / 팀원들의 리뷰 진행 
6. 병합 후 배포.
